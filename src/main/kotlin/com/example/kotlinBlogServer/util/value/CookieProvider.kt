package com.example.kotlinBlogServer.util.value

import jakarta.servlet.http.HttpServletRequest
import mu.two.KotlinLogging
import org.springframework.http.ResponseCookie
import java.util.Optional

object CookieProvider {
    private val log = KotlinLogging.logger {  }

    fun createNullCookie(cookieName: String): String{
        TODO()
    }

    fun createCookie(cookieName: String, value: String, maxAge: Long): ResponseCookie {
        return ResponseCookie.from(cookieName, value)
            .httpOnly(true)
            .secure(false)
            .path("/")
            .maxAge(maxAge)
            .build()
    }

    fun getCookie(request: HttpServletRequest, cookieName: String): Optional<String> {
        val cookieValue = request.cookies.filter { cookie ->
            cookie.name == cookieName
        }.map { cookie ->
            cookie.value
        }.firstOrNull()

        log.info { "cookieValue: $cookieValue" }

        return Optional.ofNullable(cookieValue)
    }

}