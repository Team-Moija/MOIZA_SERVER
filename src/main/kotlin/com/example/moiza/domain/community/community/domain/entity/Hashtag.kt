package com.example.moiza.domain.community.community.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.OneToMany
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "hashtag")
class Hashtag(
    name: String
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    val id: Long = 0L

    @Column(name = "name", columnDefinition = "VARCHAR(50)", nullable = false, unique = true)
    var name: String = name
        protected set

    @OneToMany(mappedBy = "hashtag")
    var communityHashtags: MutableList<CommunityHashtag> = mutableListOf()
}
