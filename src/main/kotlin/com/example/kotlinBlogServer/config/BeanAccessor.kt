package com.example.kotlinBlogServer.config

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Configuration
import kotlin.reflect.KClass

@Configuration(proxyBeanMethods = false)
class BeanAccessor(

) :ApplicationContextAware{
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        BeanAccessor.applicationContext = applicationContext
    }

    companion object{
        private lateinit var applicationContext: ApplicationContext

        fun <T : Any> getBean(type: KClass<T>): T {
            return applicationContext.getBean(type.java)
        }

        fun <T : Any> getBean(name: String, type: KClass<T>): T {
            return applicationContext.getBean(name, type.java)
        }
    }
}