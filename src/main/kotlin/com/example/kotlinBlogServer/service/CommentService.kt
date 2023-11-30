package com.example.kotlinBlogServer.service

import com.example.kotlinBlogServer.domain.comment.Comment
import com.example.kotlinBlogServer.domain.comment.CommentRepository
import com.example.kotlinBlogServer.domain.comment.CommentRes
import com.example.kotlinBlogServer.domain.comment.CommentSaveDto
import com.example.kotlinBlogServer.domain.post.PostRepository
import com.example.kotlinBlogServer.exception.PostNotFoundException
import mu.two.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService (
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository
) {
    private val log = KotlinLogging.logger {  }

    @Transactional
    fun saveComment(dto: CommentSaveDto): CommentRes {
        val post = postRepository.findById(dto.postId).orElseThrow { throw PostNotFoundException(dto.postId.toString()) }
        val comment: Comment = commentRepository.saveComment(dto.toEntity(post = post))

        commentRepository.saveClosureComment(comment.id, dto.idAncestor)

        return comment.toDto()
    }
}