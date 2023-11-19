package com.example.kotlinBlogServer.util.`fun`

import com.example.kotlinBlogServer.domain.post.Post
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl

fun SpringDataCriteriaQueryDsl<Post>.dynamicQuery(
    title: String? = null,
    email: String? = null,
    content: String? = null,
): PredicateSpec {
    return and(
        title?.let{ column(Post::title).like(title)},
        email?.let{ column(Post::title).like(email)},
        content?.let{ column(Post::title).like(content)},
    )
}