package com.example.moiza.domain.community.poll.domain.entity

import com.example.moiza.domain.community.community.domain.entity.Community
import com.example.moiza.domain.community.community.domain.entity.type.CommunityType
import com.example.moiza.domain.user.domain.User
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity(name = "poll")
class Poll(
    title: String,
    content: String,
    user: User
) : Community(
    title = title,
    content = content,
    user = user,
    type = CommunityType.POLL
) {
    @OneToMany(mappedBy = "poll", cascade = [CascadeType.ALL], orphanRemoval = true)
    var options: MutableList<PollOption> = mutableListOf()
        protected set

    @OneToMany(mappedBy = "poll", cascade = [CascadeType.ALL], orphanRemoval = true)
    var pollHashtags: MutableList<PollHashtag> =  mutableListOf()
        protected set

    fun addOptions(newOptions: List<PollOption>) {
        options.addAll(newOptions)
    }
}

