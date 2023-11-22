package com.example.kotlinBlogServer.config.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import mu.two.KotlinLogging
import org.slf4j.MDC
import java.util.UUID

class MDCLoggingFilter : Filter{
    val logger = KotlinLogging.logger {  }
    override fun doFilter(request: ServletRequest, response: ServletResponse?, chain: FilterChain) {
        val uuid = UUID.randomUUID()
        
        MDC.put("request_id", uuid.toString())
        chain.doFilter(request, response)
        MDC.clear()
    }
}