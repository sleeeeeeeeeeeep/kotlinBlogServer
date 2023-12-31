package com.example.kotlinBlogServer.service

import com.example.kotlinBlogServer.config.security.PrincipalDetails
import com.example.kotlinBlogServer.domain.member.LoginDto
import com.example.kotlinBlogServer.domain.member.MemberRepository
import com.example.kotlinBlogServer.domain.member.MemberRes
import mu.two.KotlinLogging
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val memberRepository: MemberRepository
): UserDetailsService {
    val log = KotlinLogging.logger {  }
    override fun loadUserByUsername(email: String): UserDetails {
        log.info { "loadUserByUsername 호출" }

        val member = memberRepository.findMemberByEmail(email)
        log.info { "멤버 $member" }
        return PrincipalDetails(member)
    }

    @Transactional
    fun saveMember(dto: LoginDto): MemberRes {
        return memberRepository.save(dto.toEntity()).toDto()
    }
}