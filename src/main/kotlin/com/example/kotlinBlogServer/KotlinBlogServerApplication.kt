package com.example.kotlinBlogServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinBlogServerApplication

fun main(args: Array<String>) {
	runApplication<KotlinBlogServerApplication>(*args)
}
