package com.example.moiza.domain.community.community.domain.entity

import com.example.moiza.domain.community.community.domain.entity.type.CommunityType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Column
import jakarta.persistence.Enumerated
import jakarta.persistence.EnumType
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn
import jakarta.persistence.FetchType
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "community_hashtag")
class CommunityHashtag(
    communityId: Long,
    communityType: CommunityType,
    hashtag: Hashtag
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    val id: Long = 0L

    @Column(name = "community_id", nullable = false)
    var communityId: Long = communityId
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "community_type", nullable = false)
    var communityType: CommunityType = communityType
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    var hashtag: Hashtag = hashtag
        protected set
}
