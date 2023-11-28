package com.example.kotlinBlogServer.domain.post

import com.example.kotlinBlogServer.domain.member.Member
import com.example.kotlinBlogServer.domain.member.MemberRes
import com.example.kotlinBlogServer.util.dto.BaseDto
import jakarta.validation.constraints.NotNull

data class PostSaveReq(
    @field:NotNull(message = "제목 없음")
    val title: String?,
    val content: String?,
    @field:NotNull(message = "멤버 없음")
    val memberId: Long?,
) {

    fun toEntity(): Post {
        return Post(
            title = this.title ?: "",
            content = this.content ?: "",
            member = Member.createFakeMember(memberId!!)
        )
    }
}

data class PostRes (
    val title: String,
    val content: String,
    val member: MemberRes
) : BaseDto()
