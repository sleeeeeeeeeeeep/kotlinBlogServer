package com.example.kotlinBlogServer.domain.comment

import com.example.kotlinBlogServer.domain.AuditingEntity
import com.example.kotlinBlogServer.domain.member.Member
import com.example.kotlinBlogServer.domain.post.Post
import jakarta.persistence.*

@Entity
@Table(name = "Comment")
class Comment(
    id: Long = 0,
    content: String,
    post: Post,
    member: Member
) : AuditingEntity(id) {

    @Column(name = "content", nullable = false)
    var content: String = content
        private set

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post::class)
    var post: Post = post
        private set

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class)
    var member: Member = member
        private set


    fun toDto(): CommentRes {
        val dto = CommentRes(
            member = this.member.toDto(),
            content = this.content
        )

        setBaseDtoProperty(dto)
        return dto
    }

    override fun toString(): String {
        return "Comment(content='$content', post=$post, member=$member)"
    }


}