package com.example.kotlinBlogServer.config.security

import com.example.kotlinBlogServer.domain.member.LoginDto
import com.example.kotlinBlogServer.util.value.CookieProvider
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.two.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.concurrent.TimeUnit

class CustomUserNameAuthenticationFilter (
    private val om: ObjectMapper
) : UsernamePasswordAuthenticationFilter() {

    private val log = KotlinLogging.logger {  }
    private val jwtManager = JwtManager()

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        log.info { "로그인 요청" }

        lateinit var loginDto: LoginDto

        try {
            loginDto = om.readValue(request?.inputStream, LoginDto::class.java)
            log.info { "login Dto : $loginDto" }
        } catch (e:Exception) {
            log.error { "로그인 필터: 로그인 dto 생성 실패    $e" }
        }

        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password)

        return authenticationManager.authenticate(authenticationToken)
    }


    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        log.info { "로그인 완료 jwt 토큰 만들어서 응답" }

        val principalDetails = authResult?.principal as PrincipalDetails
        val accessToken = jwtManager.generateAccessToken(om.writeValueAsString(principalDetails))
        val refreshToken = jwtManager.generateAccessToken(om.writeValueAsString(principalDetails))

        val refreshCookie = CookieProvider.createCookie(
            "refreshCookie",
            refreshToken,
            TimeUnit.DAYS.toSeconds(jwtManager.refreshTokenExpireDay)
        )

        response?.addHeader(jwtManager.authorizationHeader, "${jwtManager.jwtHeader} $accessToken")
        response?.addHeader(HttpHeaders.SET_COOKIE, refreshToken.toString())


    }
}