package com.example.kotlinBlogServer.exception

sealed class BusinessException: RuntimeException {

    var errorCode:ErrorCode

    constructor(errorCode: ErrorCode): super(errorCode.message){
        this.errorCode = errorCode
    }

    constructor(message: String?, errorCode: ErrorCode): super(errorCode.message){
        this.errorCode = errorCode
    }
}