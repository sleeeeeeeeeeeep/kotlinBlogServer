package com.example.kotlinBlogServer.domain.member

import com.example.kotlinBlogServer.config.BeanAccessor
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotNull
import org.springframework.security.crypto.password.PasswordEncoder
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
    val rawPassword: String?,

    val role: Role?,
) {
    fun toEntity(): Member {
        return Member(
            email = this.email ?: "",
            password = encodeRawPassword() ?: "",
            role = this.role?: Role.USER
        )
    }

    private fun encodeRawPassword(): String =
        BeanAccessor.getBean(PasswordEncoder::class).encode(this.rawPassword)
}

data class MemberRes(
    val id: Long,
    val email: String,
    val password: String,
    val role: Role,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    val createdAt: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    val updatedAt: LocalDateTime,
)