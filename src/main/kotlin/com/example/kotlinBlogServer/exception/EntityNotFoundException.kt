package com.example.kotlinBlogServer.exception

sealed class EntityNotFoundException(message: String?): BusinessException(
    message,
    ErrorCode.ENTITY_NOT_FOUND
) {
}

class MemberNotFoundException(id:String): EntityNotFoundException(
    "$id 없음"
)

class CommentNotFoundException(id:String): EntityNotFoundException(
    "$id 없음"
)

class PostNotFoundException(id:String): EntityNotFoundException(
    "$id 없음"
)