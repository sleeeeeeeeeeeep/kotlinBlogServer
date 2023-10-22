package com.example.kotlinBlogServer.domain.post

import com.example.kotlinBlogServer.domain.member.Member
import com.example.kotlinBlogServer.domain.member.MemberRes

data class PostSaveReq(
    val title: String,
    val content: String,
    val memberId: Long,
)

fun PostSaveReq.toEntity(): Post {
    return Post(
        title = this.title,
        content = this.content,
        member = Member.createFakeMember(memberId)
    )
}

data class PostRes (
    val id: Long,
    val title: String,
    val content: String,
    val member: MemberRes
)
