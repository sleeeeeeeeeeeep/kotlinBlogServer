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
    accessTokenExpireSecond: Long = 60,
    refreshTokenExpireDay: Long = 7

) {

    private val log = KotlinLogging.logger {  }

    private val accessSecretKey: String = "TempAccessSecretKey"
    private val refreshSecretKey: String = "TempRefreshSecretKey"

    private val claimEmail = "email"
    val claimPrincipal = "principal"

    private val accessTokenExpireSecond = accessTokenExpireSecond
    val refreshTokenExpireDay = refreshTokenExpireDay

    val authorizationHeader = "Authorization"
    val jwtHeader = "Bearer"
    private val jwtSubject = "my-token"


    fun generateRefreshToken(principal: String): String{
        val expireDate = Date(
            System.currentTimeMillis() + TimeUnit.DAYS.toMillis(refreshTokenExpireDay)
        )

        log.info { "refresh token 만료: $expireDate" }

        return generateToken(expireDate, principal, refreshSecretKey)
    }

    fun generateAccessToken(principal: String): String {
        val expireDate = Date(
            System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(accessTokenExpireSecond)
        )

        log.info { "access token 만료: $expireDate" }

        return generateToken(expireDate, principal, accessSecretKey)
    }

    private fun generateToken(
        expireDate: Date,
        principal: String,
        secretKey: String
    ): String = JWT.create()
        .withSubject(jwtSubject)
        .withExpiresAt(expireDate)
        .withClaim(claimPrincipal, principal)
        .sign(Algorithm.HMAC512(secretKey))

    fun getMemberEmail(token: String): String? {
        return JWT.require(Algorithm.HMAC512(accessSecretKey)).build()
            .verify(token)
            .getClaim(claimEmail)
            .asString()
    }

    fun getPrincipalByAccessToken(accessToken: String): String {

        val decodedJWT = getDecodeJwt(secretKey = accessSecretKey, token = accessToken)
        return decodedJWT.getClaim(claimPrincipal).asString()

    }

    fun getPrincipalByRefreshToken(refreshToken: String): String {

        val decodedJWT = getDecodeJwt(secretKey = refreshSecretKey, token = refreshToken)
        return decodedJWT.getClaim(claimPrincipal).asString()

    }

    private fun getDecodeJwt(secretKey: String, token: String): DecodedJWT {
        val verifier: JWTVerifier = JWT.require(Algorithm.HMAC512(secretKey))
            .build()

        return verifier.verify(token)
    }

    fun validAccessToken(token: String): TokenValidResult {
        return validatedJwt(secretKey = accessSecretKey, token = token)
    }

    fun validRefreshToken(token: String): TokenValidResult {
        return validatedJwt(secretKey = refreshSecretKey, token = token)
    }

    fun validatedJwt(secretKey: String, token: String): TokenValidResult {
        return try {

            getDecodeJwt(secretKey, token)
            TokenValidResult.Success()

        } catch (e: JWTVerificationException) {

            //log.error{ "error: ${e.stackTraceToString()}" }
            TokenValidResult.Failure(e)

        }
    }


}

sealed class TokenValidResult {
    class Success(val successValue: Boolean = true): TokenValidResult()
    class Failure(val exception: JWTVerificationException): TokenValidResult()
}