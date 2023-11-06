package com.example.kotlinBlogServer.domain.post

import com.example.kotlinBlogServer.domain.AuditingEntity
import com.example.kotlinBlogServer.domain.member.Member
import com.example.kotlinBlogServer.domain.member.toDto
import jakarta.persistence.*

@Entity
@Table(name = "Post")
class Post(
    id: Long = 0,
    title: String,
    content: String,
    member: Member

) : AuditingEntity(id) {

    @Column(name = "title", nullable = false)
    var title: String = title
        private set

    @Column(name = "content", length = 2000)
    var content: String = content
        private set

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class)
    var member: Member = member
        private set

    override fun toString(): String {
        return "Post(id = '$id', title='$title', content='$content', member=$member)"
    }

}

fun Post.toDto(): PostRes{
    return PostRes(
        id = this.id!!,
        title = this.title,
        content = this.content,
        member = this.member.toDto()
    )
}