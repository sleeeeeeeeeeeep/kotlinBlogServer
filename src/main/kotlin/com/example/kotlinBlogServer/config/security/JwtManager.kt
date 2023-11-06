package com.example.kotlinBlogServer.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.JWTVerifier
import mu.two.KotlinLogging
import java.util.*
import java.util.concurrent.TimeUnit

class JwtManager(
    private val accessTokenExpireSecond: Long = 300
) {

    private val log = KotlinLogging.logger {  }

    private val secretKey: String = "TempSecretKey"
    private val claimEmail = "email"
    val claimPrincipal = "principal"
    val authorizationHeader = "Authorization"
    val jwtHeader = "Bearer"
    private val jwtSubject = "my-token"


    fun generateAccessToken(principal: String): String {
        val expireDate = Date(
            System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(accessTokenExpireSecond)
        )

        log.info { "access token 만료: $expireDate" }

        return JWT.create()
            .withSubject(jwtSubject)
            .withExpiresAt(expireDate)
            .withClaim(claimPrincipal, principal)
            .sign(Algorithm.HMAC512(secretKey))
    }

    fun getMemberEmail(token: String): String? {
        return JWT.require(Algorithm.HMAC512(secretKey)).build()
            .verify(token)
            .getClaim(claimEmail)
            .asString()
    }

    fun getPrincipalByAccessToken(accessToken: String): String? {
        val decodedJWT = validatedJwt(accessToken)

        return decodedJWT.getClaim(claimPrincipal).asString()
    }

    fun validatedJwt(accessToken: String): DecodedJWT {
        try {
            val verifier: JWTVerifier = JWT.require(Algorithm.HMAC512(secretKey))
                .build()
            val jwt: DecodedJWT = verifier.verify(accessToken)
            val principal = jwt.getClaim(claimPrincipal).asString()

            return jwt
        } catch (e: JWTVerificationException) {
            log.error{ "error: ${e.stackTraceToString()}" }

            throw RuntimeException("jwt 유효하지 않음")
        }
    }

}