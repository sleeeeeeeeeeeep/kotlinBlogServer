package com.example.kotlinBlogServer.util.`fun`

import com.example.kotlinBlogServer.domain.member.Member
import com.example.kotlinBlogServer.domain.post.Post
import com.example.kotlinBlogServer.util.dto.SearchCondition
import com.example.kotlinBlogServer.util.dto.SearchType
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import org.slf4j.LoggerFactory


private val log = LoggerFactory.getLogger(object {}::class.java.`package`.name)

fun <T> SpringDataCriteriaQueryDsl<T>.dynamicQuery(
    searchCondition: SearchCondition?
): PredicateSpec {

    val keyword = searchCondition?.keyword
    log.debug("keyword: $keyword")

    return when(searchCondition?.searchType){
        SearchType.EMAIL -> and(keyword?.let { column(Member::email).like("%$keyword%") })
        SearchType.TITLE -> and(keyword?.let { column(Post::title).like("%$keyword%") })
        SearchType.CONTENT -> and(keyword?.let { column(Post::content).like("%$keyword%") })

        else -> { PredicateSpec.empty }
    }

}