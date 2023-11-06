package com.example.kotlinBlogServer.config.security

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

        val token = request.getHeader(jwtManager.authorizationHeader)?.replace("Bearer ", "")
        if(token == null){
            log.info { "토큰 없음" }

            chain.doFilter(request, response)
            return
        }


        log.debug("토큰: $token")

        val principalJsonData = jwtManager.getPrincipalByAccessToken(token)
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
}