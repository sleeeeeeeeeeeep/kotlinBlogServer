package com.example.kotlinBlogServer.api

import com.example.kotlinBlogServer.domain.post.PostRes
import com.example.kotlinBlogServer.service.PostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(
    private val postService: PostService
) {
    @GetMapping("/posts")
    fun findPost(@PageableDefault(size = 10) pageable: Pageable) : ResponseEntity<Page<PostRes>> {
        return ResponseEntity.ok().body(postService.findAll(pageable))
    }
}