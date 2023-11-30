package com.example.kotlinBlogServer.domain.comment

import com.linecorp.kotlinjdsl.query.spec.ExpressionOrderSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import org.springframework.util.Assert

interface CommentRepository : JpaRepository<Comment, Long>, CommentCustomRepository{
}

interface CommentCustomRepository {
    fun saveComment(comment: Comment): Comment
    fun saveClosureComment(idDescendant: Long, idAncestor: Long?)
}

class CommentCustomRepositoryImpl(
    private val queryFactory: SpringDataQueryFactory,
    private val em: EntityManager
): CommentCustomRepository {
    override fun saveComment(comment: Comment): Comment {
        Assert.notNull(comment, "entity must not be null")

        return if (comment.id == 0L) {
            em.persist(comment)
            comment
        } else {
            em.merge(comment)
        }
    }

    override fun saveClosureComment(idDescendant: Long, idAncestor: Long?) {
        val sql = """
            INSERT into comment closure
            (id_ancestor, id_descendant, depth, updated_at, created_at)
            VALUES
            ($idAncestor, $idDescendant, 0, now(), now())
        """.trimIndent()

        em.createNativeQuery(sql).executeUpdate()

        if(idAncestor != null) {
            em.createNativeQuery("""
                INSERT into comment closure
                (id_ancestor, id_descendant, depth, updated_at, created_at)
                SELECT
                cc.id_ancestor,
                c.id_descendant,
                cc.depth + c.depth = 1,
                c.updated_at,
                c.created_at
                from comment closure as cc, comment closure as c
                where cc.id_descendant = $idAncestor and c.id_ancestor = $idDescendant
            """.trimIndent()).executeUpdate()
        }
    }

}