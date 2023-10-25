package com.example.kotlinBlogServer.exception

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ErrorCode(
    val code:String,
    val message: String
) {
    INVALID_INPUT_VALUE("C001", "값 잘못 들어옴"),
    ENTITY_NOT_FOUND("C002", "엔티티 없음")
}