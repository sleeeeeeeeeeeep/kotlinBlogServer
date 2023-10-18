package com.example.kotlinBlogServer.domain.post

import com.example.kotlinBlogServer.domain.AuditingEntity
import com.example.kotlinBlogServer.domain.comment.Comment
import com.example.kotlinBlogServer.domain.member.Member
import jakarta.persistence.*

@Entity
@Table(name = "Post")
class Post(

    title: String,
    content: String,
    member: Member

) : AuditingEntity() {

    @Column(name = "title", nullable = false)
    var title: String = title
        private set

    @Column(name = "content")
    var content: String = content
        private set

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class)
    var member: Member = member
        private set

}