package com.example.kotlinBlogServer.config.aop

import mu.two.KotlinLogging

object CustomAopObject {
    private val log = KotlinLogging.logger {  }

    fun wrapTryCatchWithVoidFunc(func: () -> Unit) {
        try {
            func()
        } catch (e: Exception) {
            log.error { e.stackTraceToString() }
            //throw e
        }
    }

}