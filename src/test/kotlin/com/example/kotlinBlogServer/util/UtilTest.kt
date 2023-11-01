package com.example.kotlinBlogServer.util

import mu.two.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UtilTest {
    private val log = KotlinLogging.logger {  }

    @Test
    fun bcryptEncodeTest(){
        val encoder = BCryptPasswordEncoder()
        val encodePassword = encoder.encode("1234")

        log.info { encodePassword }
    }
}