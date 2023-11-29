package com.example.kotlinBlogServer.domain.comment

import com.example.kotlinBlogServer.domain.AuditingEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "Comment closure")
class CommentClosure (
    id: Long = 0,
    idAncestor: Comment? = null,
    idDescendant: Comment,
    depth: Int = 0
): AuditingEntity(id){
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ancestor", nullable = true)
    var idAncestor = idAncestor
        private set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_descendant", nullable = true)
    var idDescendant = idDescendant
        private set

    @Column(name = "depth")
    var depth = depth
        private set

    override fun toString(): String {
        return "CommentClosure(id=$id, idAncestor=$idAncestor, idDescendant=$idDescendant, depth=$depth)"
    }


}