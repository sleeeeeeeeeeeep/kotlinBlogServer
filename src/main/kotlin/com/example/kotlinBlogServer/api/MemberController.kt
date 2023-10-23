package com.example.kotlinBlogServer.api

import com.example.kotlinBlogServer.domain.member.MemberRes
import com.example.kotlinBlogServer.service.MemberService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/members")
    fun findAll(@PageableDefault(size = 10) pageable: Pageable): ResponseEntity<Page<MemberRes>> {
        return ResponseEntity.ok().body(memberService.findAll(pageable))
    }
}