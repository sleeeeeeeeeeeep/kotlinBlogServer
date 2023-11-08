package com.example.kotlinBlogServer.domain.member

import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

/**
 * dto <-> entity 간 매핑 스타일
 * 1. 각 dto, entity 마다 매핑로직 설정
 * 2. 인터페이스 하나 만들어서 사용
 */
data class LoginDto(
    @field:NotNull(message = "이메일 있어야 함")
    val email: String?,

    @field:NotNull(message = "비번 있어야 함")
    val password: String?,

    val role: Role?,
)

fun LoginDto.toEntity(): Member {
    return Member(
        email = this.email ?: "",
        password = this.password ?: "",
        role = this.role?: Role.USER
    )
}

data class MemberRes(
    val id: Long,
    val email: String,
    val password: String,
    val role: Role,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)