package com.example.moiza.domain.community.poll.domain.entity

import com.example.moiza.domain.community.community.domain.entity.Hashtag
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Column
import jakarta.persistence.ManyToOne
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "poll_hashtag")
class PollHashtag(
    poll: Poll,
    hashtag: Hashtag
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    val id: Long = 0L

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id")
    var poll: Poll = poll
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    var hashtag: Hashtag = hashtag
        protected set
}
