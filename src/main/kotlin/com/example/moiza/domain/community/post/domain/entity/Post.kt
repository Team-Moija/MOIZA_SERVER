package com.example.moiza.domain.community.post.domain.entity

import com.example.moiza.domain.community.community.domain.entity.Community
import com.example.moiza.domain.community.community.domain.entity.type.CommunityType
import com.example.moiza.domain.user.domain.User
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity

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
}
