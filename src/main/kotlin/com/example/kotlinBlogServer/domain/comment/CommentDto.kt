package com.example.kotlinBlogServer.domain.comment

import com.example.kotlinBlogServer.domain.member.Member
import com.example.kotlinBlogServer.domain.post.Post

data class CommentSaveDto (
    val memberId: Long,
    val content: String,
    val postId: Long
) {
    fun toEntity(post: Post): Comment {
        return Comment(
            content = this.content,
            post = post,
            member = Member.createFakeMember(this.memberId)
        )
    }
}