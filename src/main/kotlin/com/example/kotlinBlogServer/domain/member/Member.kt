package com.example.kotlinBlogServer.domain.member

import com.example.kotlinBlogServer.domain.AuditingEntity
import com.example.kotlinBlogServer.domain.comment.Comment
import com.example.kotlinBlogServer.domain.post.Post
import jakarta.persistence.*

@Entity
@Table(name = "Member")
class Member(

    email: String,
    password: String,
    role: Role,

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

    override fun toString(): String {
        return "Member(email='$email', password='$password', role=$role)"
    }

}

enum class Role{
    USER, ADMIN
}