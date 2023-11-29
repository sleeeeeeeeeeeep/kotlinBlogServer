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

}