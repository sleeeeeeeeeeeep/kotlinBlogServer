package com.example.kotlinBlogServer.config.aop

import com.fasterxml.jackson.databind.ObjectMapper
import mu.two.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Component
@Aspect
class LoggerAspect {
    val log = KotlinLogging.logger {}

    @Pointcut("execution(* com.example.kotlinBlogServer.api.*Controller.*(..))")
    private fun controllerCut() = Unit

    @Before("controllerCut()")
    fun controllerLoggerAdvice(joinPoint: JoinPoint){
        val typeName = joinPoint.signature.declaringTypeName
        val methodName = joinPoint.signature.name
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request

        log.info{
            """
                
                request url: ${request.servletPath}
                type: $typeName
                methodName: $methodName
                
            """.trimIndent()
        }
    }

    @AfterReturning(pointcut = "controllerCut()", returning = "result")
    fun controllerLogAfter(joinPoint: JoinPoint, result: Any?){
        log.info {
            """
            ${joinPoint.signature.name} 
            메소드 리턴 값: $result" 
            
        """.trimIndent()
        }
    }
}