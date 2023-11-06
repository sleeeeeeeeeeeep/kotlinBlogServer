package com.example.kotlinBlogServer.util

import com.example.kotlinBlogServer.config.security.JwtManager
import com.example.kotlinBlogServer.config.security.PrincipalDetails
import com.example.kotlinBlogServer.domain.member.Member
import com.fasterxml.jackson.databind.ObjectMapper
import mu.two.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UtilTest {
    private val log = KotlinLogging.logger {  }
    private val mapper = ObjectMapper()

    @Test
    fun bcryptEncodeTest(){
        val encoder = BCryptPasswordEncoder()
        val encodePassword = encoder.encode("1234")

        log.info { encodePassword }
    }

    @Test
    fun generateJwtTest() {
        val jwtManager = JwtManager(accessTokenExpireSecond = 60)
        val details = PrincipalDetails(Member.createFakeMember(1))
        val jsonPrincipal = mapper.writeValueAsString(details)
        val accessToken = jwtManager.generateAccessToken(jsonPrincipal)
        val decodedJWT = jwtManager.validatedJwt(accessToken)

        val principalString = decodedJWT.getClaim(jwtManager.claimPrincipal).asString()
        val principalDetails: PrincipalDetails = mapper.readValue(principalString, PrincipalDetails::class.java)

        log.info { "결과: ${principalDetails.member}" }

        details.authorities.forEach{
            println(it.authority)
        }
    }
}