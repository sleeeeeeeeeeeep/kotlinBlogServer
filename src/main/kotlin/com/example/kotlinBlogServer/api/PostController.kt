package com.example.kotlinBlogServer.api

import com.example.kotlinBlogServer.domain.post.PostRes
import com.example.kotlinBlogServer.domain.post.PostSaveReq
import com.example.kotlinBlogServer.service.PostService
import com.example.kotlinBlogServer.util.dto.Search
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/v1")
@RestController
class PostController(
    private val postService: PostService
) {
    @GetMapping("/posts")
    fun findPost(@PageableDefault(size = 10) pageable: Pageable,
                 @RequestBody search: Search) : ResponseEntity<Page<PostRes>> {
        return ResponseEntity.ok().body(postService.findAll(pageable, search))
    }

    @GetMapping("/post/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<PostRes> {
        return ResponseEntity.ok().body(postService.findPostById(id))
    }

    @DeleteMapping("/post/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(postService.deleteMember(id))
    }

    @PostMapping("/post")
    fun save(@Valid @RequestBody dto: PostSaveReq): PostRes {
        return postService.savePost(dto)
    }
}