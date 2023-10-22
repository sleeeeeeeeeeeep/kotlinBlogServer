package com.example.kotlinBlogServer.domain.member

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface MemberRepository : JpaRepository<Member, Long>{
}

@Repository
interface MemberCustomRepository {
}

class MemeberCustomRepositoryImpl(

    private val jpaQueryFactory: JPAQueryFactory

): MemberCustomRepository {
}