package com.example.kotlinBlogServer.service

import com.example.kotlinBlogServer.domain.member.Member
import com.example.kotlinBlogServer.domain.member.MemberRepository
import com.example.kotlinBlogServer.domain.member.MemberRes
import com.example.kotlinBlogServer.domain.member.toDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    @Transactional(readOnly = true)
    fun findAll(): List<MemberRes> =
        memberRepository.findAll().map {
            it.toDto()
        }

}