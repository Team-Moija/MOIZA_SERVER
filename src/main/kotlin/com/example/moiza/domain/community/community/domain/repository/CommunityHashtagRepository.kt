package com.example.moiza.domain.community.community.domain.repository

import com.example.moiza.domain.community.community.domain.entity.CommunityHashtag
import com.example.moiza.domain.community.community.domain.entity.type.CommunityType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommunityHashtagRepository : JpaRepository<CommunityHashtag, Long> {
    fun existsByCommunityIdAndCommunityTypeAndHashtagId(communityId: Long, communityType: CommunityType, hashtagId: Long): Boolean

    fun findByCommunityIdAndCommunityTypeAndHashtagId(communityId: Long, communityType: CommunityType, hashtagId: Long): CommunityHashtag?
}
