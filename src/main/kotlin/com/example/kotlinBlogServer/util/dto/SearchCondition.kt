package com.example.kotlinBlogServer.util.dto

import com.fasterxml.jackson.annotation.JsonCreator

data class SearchCondition(
    val searchType: SearchType?,
    val keyword: String?
)

data class Search(
    val searchFlag: String?,
    val keyword: String?
)

fun Search.convertToSearchCondition(): SearchCondition {
    var searchType: SearchType ?= null

    when(this.searchFlag?.lowercase()) {
        "email" -> searchType = SearchType.EMAIL
        "title" -> searchType = SearchType.TITLE
        "content" -> searchType = SearchType.CONTENT
    }

    return SearchCondition(searchType, this.keyword)
}

enum class SearchType {
    EMAIL,
    TITLE,
    CONTENT;
}