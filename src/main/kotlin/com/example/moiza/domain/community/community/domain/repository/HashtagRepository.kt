package com.example.moiza.domain.community.community.domain.repository

import com.example.moiza.domain.community.community.domain.entity.Hashtag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HashtagRepository : JpaRepository<Hashtag, Long> {
    fun findByName(name: String): Hashtag?
}
