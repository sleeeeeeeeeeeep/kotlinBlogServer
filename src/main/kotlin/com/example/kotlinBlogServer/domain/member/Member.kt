package com.example.kotlinBlogServer.domain.member

import com.example.kotlinBlogServer.domain.AuditingEntity
import jakarta.persistence.*

@Entity
@Table(name = "Member")
class Member(

    email: String,
    password: String,
    role: Role

) : AuditingEntity() {

    @Column(name = "email", nullable = false)
    var email: String = email
        private set

    @Column(name = "password", nullable = false)
    var password: String = password
        private set

    @Enumerated(EnumType.STRING)
    var role: Role = role
        private set

}

enum class Role{
    USER, ADMIN
}