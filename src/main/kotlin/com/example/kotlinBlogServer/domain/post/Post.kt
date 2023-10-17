package com.example.kotlinBlogServer.domain.post

import com.example.kotlinBlogServer.domain.AuditingEntity
import jakarta.persistence.*

@Entity
@Table(name = "Post")
class Post(

    title: String,
    content: String

) : AuditingEntity() {

    @Column(name = "title", nullable = false)
    var title: String = title
        private set

    @Column(name = "content")
    var content: String = content
        private set

}