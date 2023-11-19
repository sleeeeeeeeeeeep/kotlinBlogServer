package com.example.kotlinBlogServer.api

import com.example.kotlinBlogServer.util.dto.SearchCondition
import com.example.kotlinBlogServer.util.dto.SearchType
import mu.two.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    private val log = KotlinLogging.logger {  }

    @GetMapping("/health")
    fun healthTest(): String = "hello kotlin-blog"

    @GetMapping("/enum")
    fun enumTest(searchCondition: SearchCondition): String {
        log.info { """
            $searchCondition
            ${searchCondition.searchType}
            ${searchCondition.keyword}
            
        """.trimIndent() }

        return "test"
    }
}