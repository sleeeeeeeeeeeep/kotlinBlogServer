package com.example.kotlinBlogServer.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface MemberRepository : JpaRepository<Member, Long>{
}