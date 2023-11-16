package com.example.kotlinBlogServer.api

import com.example.kotlinBlogServer.domain.member.LoginDto
import com.example.kotlinBlogServer.domain.member.MemberRes
import com.example.kotlinBlogServer.service.AuthService
import jakarta.servlet.http.HttpSession
import jakarta.validation.Valid
import mu.two.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController (
    private val authService: AuthService
) {
    val log = KotlinLogging.logger {  }

    @PostMapping("/login")
    fun login(session: HttpSession){
        session.setAttribute("principal", "pass")
    }

    @PostMapping("/member")
    fun joinApp(@Valid @RequestBody dto: LoginDto): ResponseEntity<MemberRes> {
        return ResponseEntity.ok().body(authService.saveMember(dto))
    }
}