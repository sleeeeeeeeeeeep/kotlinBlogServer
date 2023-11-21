package com.example.kotlinBlogServer.api

import com.example.kotlinBlogServer.domain.member.MemberRes
import com.example.kotlinBlogServer.service.MemberService
import jakarta.servlet.http.HttpSession
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/members")
    fun findAll(@PageableDefault(size = 10) pageable: Pageable, session: HttpSession): ResponseEntity<Page<MemberRes>> {
        return ResponseEntity.ok().body(memberService.findAll(pageable))
    }

    @GetMapping("/member/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<MemberRes> {
        return ResponseEntity.ok().body(memberService.findMemberById(id))
    }

    @DeleteMapping("/member/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(memberService.deleteMember(id))
    }

}