package com.example.kotlinBlogServer.api

import com.example.kotlinBlogServer.domain.member.Member
import com.example.kotlinBlogServer.service.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/members")
    fun findAll(): MutableList<Member> {
        return memberService.findAll();
    }
}