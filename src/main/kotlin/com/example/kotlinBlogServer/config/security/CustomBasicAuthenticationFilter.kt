package com.example.kotlinBlogServer.config.security

import com.auth0.jwt.exceptions.TokenExpiredException
import com.example.kotlinBlogServer.domain.member.MemberRepository
import com.example.kotlinBlogServer.util.value.CookieProvider
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.two.KotlinLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

class CustomBasicAuthenticationFilter(
    private val memberRepository: MemberRepository,
    private val om: ObjectMapper,
    authenticationManager: AuthenticationManager
): BasicAuthenticationFilter(authenticationManager) {

    val log = KotlinLogging.logger {  }

    private val jwtManager = JwtManager()
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        log.info { "권한 필요한 요청" }

        val accessToken = request.getHeader(jwtManager.authorizationHeader)?.replace("Bearer ", "")
        if(accessToken == null){
            log.info { "토큰 없음" }
            chain.doFilter(request, response)
            return
        }

        log.debug("액세스 토큰: $accessToken")
        val accessTokenValidResult = jwtManager.validAccessToken(accessToken)
        if(accessTokenValidResult is TokenValidResult.Failure){

            handleTokenException(accessTokenValidResult) {
                log.info { accessTokenValidResult.exception.javaClass }

                val refreshToken = CookieProvider.getCookie(request, CookieProvider.CookieName.REFRESH_COOKIE).orElseThrow()
                val refreshTokenResult = jwtManager.validRefreshToken(refreshToken)

                if(refreshTokenResult is TokenValidResult.Failure){
                    throw RuntimeException("리프레시 토큰 유효하지 않음")
                }

                val principalString = jwtManager.getPrincipalByRefreshToken(refreshToken)
                val details = om.readValue(principalString, PrincipalDetails::class.java)

                reissueAccessToken(details, response)
                setAuthentication(details, chain, request, response)

            }
            return
        }

        val principalJsonData = jwtManager.getPrincipalByAccessToken(accessToken)
        val principalDetails = om.readValue(principalJsonData, PrincipalDetails::class.java)

        setAuthentication(principalDetails, chain, request, response)
    }

    private fun setAuthentication(
        principalDetails: PrincipalDetails,
        chain: FilterChain,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        val authentication: Authentication = UsernamePasswordAuthenticationToken(
            principalDetails,
            principalDetails.password,
            principalDetails.authorities
        )

        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    private fun reissueAccessToken(
        details: PrincipalDetails?,
        response: HttpServletResponse
    ) {
        val accessToken = jwtManager.generateAccessToken(om.writeValueAsString(details))
        response.addHeader(jwtManager.authorizationHeader, "${jwtManager.jwtHeader} $accessToken")
    }

    private fun handleTokenException(tokenValidResult: TokenValidResult.Failure, func:() -> Unit){
        when(tokenValidResult.exception){
            is TokenExpiredException -> func()
            else -> {
                log.error(tokenValidResult.exception.stackTraceToString())

                throw tokenValidResult.exception
            }
        }
    }

}