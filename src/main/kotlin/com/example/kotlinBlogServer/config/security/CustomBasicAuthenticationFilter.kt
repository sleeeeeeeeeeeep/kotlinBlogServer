package com.example.kotlinBlogServer.config.security

import com.auth0.jwt.exceptions.TokenExpiredException
import com.example.kotlinBlogServer.domain.member.MemberRepository
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
            if(accessTokenValidResult.exception is TokenExpiredException) {
                log.info { accessTokenValidResult }
            } else {
                log.error { accessTokenValidResult.exception.stackTraceToString() }
            }
        }

        val principalJsonData = jwtManager.getPrincipalByAccessToken(accessToken)
        val principalDetails = om.readValue(principalJsonData, PrincipalDetails::class.java)

        // val member = memberRepository.findMemberByEmail(details.member.email)
        // val principalDetails = PrincipalDetails(member)

        val authentication: Authentication = UsernamePasswordAuthenticationToken(
            principalDetails,
            principalDetails.password,
            principalDetails.authorities
        )

        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

//    private fun reissueAccessToken(
//        e: JWTVerificationException,
//        request: HttpServletRequest?
//    ) {
//        if (e is TokenExpiredException) {
//            val refreshToken = CookieProvider.getCookie(request!!, "refreshCookie").orElseThrow()
//            val validateJwt = validatedJwt(refreshToken)
//
//            val principalString = getPrincipalByAccessToken(refreshToken)
//            val principalDetails = ObjectMapper().readValue(principalString, PrincipalDetails::class.java)
//
//            val authentication: Authentication = UsernamePasswordAuthenticationToken(
//                principalDetails,
//                principalDetails.password,
//                principalDetails.authorities
//            )
//
//            SecurityContextHolder.getContext().authentication = authentication
//        }
//    }
}