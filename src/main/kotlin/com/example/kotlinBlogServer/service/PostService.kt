package com.example.kotlinBlogServer.service

import com.example.kotlinBlogServer.domain.post.*
import com.example.kotlinBlogServer.util.dto.Search
import com.example.kotlinBlogServer.util.dto.convertToSearchCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService (
    private val postRepository: PostRepository
) {
    //@PreAuthorize("hasAuthority('ADMIN')")
    @Secured(*["ROLE_SUPER", "ROLE_ADMIN"])
    @Transactional(readOnly = true)
    fun findAll(pageable: Pageable, search: Search): Page<PostRes> {
        val searchCondition = search.convertToSearchCondition()

        return postRepository.findPosts(pageable, searchCondition).map {
            it.toDto()
        }
    }

    @Transactional
    fun savePost(dto: PostSaveReq): PostRes {
        return postRepository.save(dto.toEntity()).toDto()
    }

    @Transactional
    fun deleteMember(id: Long){
        return postRepository.deleteById(id)
    }

    @Transactional(readOnly = true)
    fun findPostById(id: Long): PostRes {
        return postRepository.findById(id).orElseThrow().toDto()
    }
}