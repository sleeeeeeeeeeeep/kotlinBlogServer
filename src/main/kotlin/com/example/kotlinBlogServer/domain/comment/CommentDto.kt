package com.example.kotlinBlogServer.domain.comment

import com.example.kotlinBlogServer.domain.member.Member
import com.example.kotlinBlogServer.domain.member.MemberRes
import com.example.kotlinBlogServer.domain.post.Post
import com.example.kotlinBlogServer.util.dto.BaseDto

data class CommentSaveDto (
    val memberId: Long,
    val content: String,
    val postId: Long,
    val idAncestor: Long?
) {
    fun toEntity(post: Post): Comment {
        return Comment(
            content = this.content,
            post = post,
            member = Member.createFakeMember(this.memberId)
        )
    }
}

data class CommentRes (
    var content: String,
    var member: MemberRes
): BaseDto()