package com.example.kotlinBlogServer.config

import com.example.kotlinBlogServer.domain.member.LoginDto
import com.example.kotlinBlogServer.domain.member.Member
import com.example.kotlinBlogServer.domain.member.MemberRepository
import com.example.kotlinBlogServer.domain.member.Role
import com.example.kotlinBlogServer.domain.post.Post
import com.example.kotlinBlogServer.domain.post.PostRepository
import com.example.kotlinBlogServer.domain.post.PostSaveReq
import com.example.kotlinBlogServer.domain.post.toEntity
import io.github.serpro69.kfaker.faker
import mu.two.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

// 더미데이터 생성용

@Configuration
class InitData(
    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository,
    private val encoder: BCryptPasswordEncoder
) {
    val faker = faker { }
    private val log = KotlinLogging.logger { }


    @EventListener(ApplicationReadyEvent::class)
    private fun init(){
        log.info("와 로그다")

//        val members = generateMembers(100)
//        memberRepository.saveAll(members)
//
//        val posts = generatePosts(100)
//        postRepository.saveAll(posts)
    }

    private fun generateMembers(cnt: Int) : MutableList<Member> {
        // arraylist 같은 친구
        val members = mutableListOf<Member>()

        for (i in 1..cnt){
            val member = generateMember()
            log.info("더미 멤버 $member")
            members.add(member)
        }

        return members
    }

    private fun generatePosts(cnt: Int) : MutableList<Post> {
        // arraylist 같은 친구
        val posts = mutableListOf<Post>()

        for (i in 1..cnt){
            val post = generatePost()
            log.info("더미 포스트 $post")
            posts.add(post)
        }

        return posts
    }

    // 코틀린은 마지막 변수가 리턴임
    // 바로 리턴 ㄱ
    private fun generateMember(): Member = LoginDto(
        email = faker.internet.safeEmail(),
        rawPassword = "1234",
        role = Role.USER
    ).toEntity()

    private fun generatePost(): Post = PostSaveReq(
        title = faker.theExpanse.ships(),
        content = faker.quote.matz(),
        memberId = 1
    ).toEntity()
}