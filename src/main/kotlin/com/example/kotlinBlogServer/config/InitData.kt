package com.example.kotlinBlogServer.config

import com.example.kotlinBlogServer.domain.member.Member
import com.example.kotlinBlogServer.domain.member.MemberRepository
import com.example.kotlinBlogServer.domain.member.Role
import io.github.serpro69.kfaker.faker
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

// 더미데이터 생성용

@Configuration
class InitData(
    private val memberRepository: MemberRepository
) {
    val faker = faker { }

    @EventListener(ApplicationReadyEvent::class)
    private fun init(){
        val member = Member(
            email = faker.internet.safeEmail(),
            password = "1234",
            role = Role.USER
        )

        memberRepository.save(member)
    }

}