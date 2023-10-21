package com.example.kotlinBlogServer.domain.member

/**
 * dto <-> entity 간 매핑 스타일
 * 1. 각 dto, entity 마다 매핑로직 설정
 * 2. 인터페이스 하나 만들어서 사용
 */
data class MemberSaveReq(
    val email: String,
    val password: String,
    val role: Role,
)

fun MemberSaveReq.toEntity(): Member {
    return Member(
        email = this.email,
        password = this.password,
        role = this.role
    )
}

data class MemberRes(
    val id: Long,
    val email: String,
    val password: String,
    val role: Role,
)