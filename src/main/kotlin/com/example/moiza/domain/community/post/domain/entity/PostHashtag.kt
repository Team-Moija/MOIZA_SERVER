package com.example.moiza.domain.community.post.domain.entity

import com.example.moiza.domain.community.community.domain.entity.Hashtag
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Column
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn
import jakarta.persistence.GenerationType
import jakarta.persistence.FetchType
import jakarta.persistence.Id

@Entity(name = "post_hashtag")
class PostHashtag(
    post: Post,
    hashtag: Hashtag
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    val id: Long = 0L

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post = post
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    var hashtag: Hashtag = hashtag
        protected set
}
