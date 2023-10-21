package com.example.kotlinBlogServer.config

import com.example.kotlinBlogServer.domain.member.*
import io.github.serpro69.kfaker.faker
import mu.two.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

// 더미데이터 생성용

@Configuration
class InitData(
    private val memberRepository: MemberRepository
) {
    val faker = faker { }
    private val log = KotlinLogging.logger { }


    @EventListener(ApplicationReadyEvent::class)
    private fun init(){
        log.info("와 로그다")

        // arraylist 같은 친구
        val members = mutableListOf<Member>()

        for (i in 1..100){
            val member = generateMember()
            log.info("시작 멤버 로그 $member")
            members.add(member)
        }

        memberRepository.saveAll(members)
    }

    // 코틀린은 마지막 변수가 리턴임
    // 바로 리턴 ㄱ
    private fun generateMember(): Member = MemberSaveDto(
            email = faker.internet.safeEmail(),
            password = "1234",
            role = Role.USER
        ).toEntity()
}