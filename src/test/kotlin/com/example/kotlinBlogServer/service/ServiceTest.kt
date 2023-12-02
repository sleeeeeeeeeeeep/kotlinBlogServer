package com.example.kotlinBlogServer.service

import com.example.kotlinBlogServer.domain.comment.CommentRepository
import com.example.kotlinBlogServer.domain.comment.CommentReqDto
import com.example.kotlinBlogServer.domain.member.Member
import com.example.kotlinBlogServer.domain.post.Post
import com.example.kotlinBlogServer.domain.post.PostRepository
import com.example.kotlinBlogServer.setup.MockitoHelper
import mu.two.KotlinLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class ServiceTest {

    private val log = KotlinLogging.logger {  }

    @Mock // mocking용 객체
    private lateinit var commentRepository: CommentRepository

    @Mock
    private lateinit var postRepository: PostRepository

    @InjectMocks
    private lateinit var commentService: CommentService

    @Test
    fun mockDiTest() {
        log.info { """
            ${this.commentService}
        """.trimIndent() }
    }

    @Test
    fun saveCommentTest() {
        // given
        val dto = CommentReqDto(
            memberId = 1,
            content = "test content",
            postId = 1,
            idAncestor = null
        )

        val post = Optional.ofNullable(
            Post(
                id = 1,
                title = "title",
                content = "content",
                member = Member.createFakeMember(2)
            )
        )

        val expectedPost = post.orElseThrow()
        val comment = dto.toEntity(post.orElseThrow())

        // stub
        Mockito.`when`(postRepository.findById(dto.postId)).thenReturn(post)
        Mockito.`when`(commentRepository.saveComment(MockitoHelper.anyObject())).thenReturn(comment)
        Mockito.`when`(commentRepository.saveClosureComment(0, dto.idAncestor)).thenReturn(ArgumentMatchers.anyInt())

        val saveComment = commentService.saveComment(dto)

        //then
        Assertions.assertEquals(comment.content, saveComment.content)
        Assertions.assertEquals(comment.member.id, saveComment.member.id)

    }
}