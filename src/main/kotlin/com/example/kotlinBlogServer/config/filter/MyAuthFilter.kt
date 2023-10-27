package com.example.kotlinBlogServer.config.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import mu.two.KotlinLogging

class MyAuthFilter : Filter{
    val logger = KotlinLogging.logger {  }
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        // 인증처리
        
        val servletRequest = request as HttpServletRequest
        val principal = servletRequest.session.getAttribute("principal")

        if(principal == null) {
            throw RuntimeException("세션 없음")
        } else {
            chain?.doFilter(request, response)
        }
    }
}