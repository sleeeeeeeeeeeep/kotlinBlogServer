package com.example.kotlinBlogServer.domain.post

import com.example.kotlinBlogServer.domain.AuditingEntity
import com.example.kotlinBlogServer.domain.member.Member
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

    fun toDto(): PostRes{
        val dto =  PostRes(
            title = this.title,
            content = this.content,
            member = this.member.toDto()
        )

        setBaseDtoProperty(dto)

        return dto
    }



    override fun toString(): String {
        return "Post(id = '$id', title='$title', content='$content', member=$member)"
    }

}

