package com.example.kotlinBlogServer.domain.comment

import com.example.kotlinBlogServer.domain.AuditingEntity
import com.example.kotlinBlogServer.domain.member.Member
import com.example.kotlinBlogServer.domain.post.Post
import jakarta.persistence.*

@Entity
@Table(name = "Comment")
class Comment(

    content: String,
    post: Post

) : AuditingEntity() {

    @Column(name = "content", nullable = false)
    var content: String = content
        private set

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post::class)
    var post: Post = post
        private set

}