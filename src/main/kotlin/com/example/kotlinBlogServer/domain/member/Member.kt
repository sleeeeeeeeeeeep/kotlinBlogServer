package com.example.kotlinBlogServer.domain.member

import com.example.kotlinBlogServer.domain.AuditingEntity
import jakarta.persistence.*

@Entity
@Table(name = "Member")
class Member(
    id: Long = 0,
    email: String,
    password: String,
    role: Role = Role.USER,

    ) : AuditingEntity(id) {

    @Column(name = "email", nullable = false)
    var email: String = email
        private set

    @Column(name = "password", nullable = false)
    var password: String = password
        private set

    @Enumerated(EnumType.STRING)
    var role: Role = role
        private set

    fun toDto() : MemberRes {
        return MemberRes(
            id = this.id!!,
            email = this.email,
            password = this.password,
            role = this.role,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }

    override fun toString(): String {
        return "Member(email='$email', password='$password', role=$role, createdAt=$createdAt, updatedAt=$updatedAt)"
    }

    companion object {
        fun createFakeMember(memberId: Long): Member {
            return Member(id = memberId, "adfl@fake.member", password = "1234")
        }
    }
}



enum class Role{
    USER, ADMIN
}