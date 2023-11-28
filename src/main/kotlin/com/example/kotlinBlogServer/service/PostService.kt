package com.example.kotlinBlogServer.service

import com.example.kotlinBlogServer.domain.post.PostRepository
import com.example.kotlinBlogServer.domain.post.PostRes
import com.example.kotlinBlogServer.domain.post.PostSaveReq
import com.example.kotlinBlogServer.service.common.FileUploaderService
import com.example.kotlinBlogServer.util.dto.Search
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PostService (
    private val postRepository: PostRepository,
    private val localFileUploaderServiceImpl: FileUploaderService
) {
    //@PreAuthorize("hasAuthority('ADMIN')")
    @Secured(*["ROLE_USER", "ROLE_ADMIN"])
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

    fun savePostImg(image: MultipartFile): String {
        return localFileUploaderServiceImpl.upload(image)
    }
}