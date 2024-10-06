package com.example.moiza.domain.community.post.domain.entity

import com.example.moiza.domain.community.community.domain.entity.Community
import com.example.moiza.domain.community.community.domain.entity.type.CommunityType
import com.example.moiza.domain.user.domain.User
import jakarta.persistence.CascadeType
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity(name = "post")
class Post(
    title: String,
    content: String,
    user: User,
    image: List<String>? = null
) : Community(
    title = title,
    content = content,
    user = user,
    type = CommunityType.POST
) {

    @ElementCollection
    var image: List<String>? = image
        protected set

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    var postHashtags: MutableList<PostHashtag> = mutableListOf()
        protected set
}
