package com.example.kotlinBlogServer.service

import com.example.kotlinBlogServer.domain.post.PostRepository
import com.example.kotlinBlogServer.domain.post.PostRes
import com.example.kotlinBlogServer.domain.post.toDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService (
    private val postRepository: PostRepository
) {
    @Transactional(readOnly = true)
    fun findAll(): List<PostRes> =
        postRepository.findAll().map {
            it.toDto()
        }
}