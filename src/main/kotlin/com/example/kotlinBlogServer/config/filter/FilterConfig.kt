package com.example.kotlinBlogServer.config.filter

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterConfig {
    @Bean
    fun registMyAuthFilter(): FilterRegistrationBean<MyAuthFilter> {
        val bean = FilterRegistrationBean(MyAuthFilter())
        bean.addUrlPatterns("/api/*")
        bean.order = 0

        return bean
    }
}