package com.example.kotlinBlogServer.config

import mu.two.KotlinLogging
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.HandlerTypePredicate
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(

) : WebMvcConfigurer {
    private val log = KotlinLogging.logger {  }

    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        val apiVersion = "/v1"
        val basePackage = "com.example.kotlinBlogServer.api"

        configurer.addPathPrefix(
            apiVersion,
            HandlerTypePredicate.forBasePackage(basePackage)
        )
    }
}