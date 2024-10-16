package com.example.moiza.domain.community.post.presentation.dto.res

import com.example.moiza.domain.community.community.domain.entity.type.CommunityType
import com.example.moiza.domain.user.presentation.dto.res.UserResponse
import java.time.LocalDateTime

class PostDetailResponse(
    val postId: Long,
    val title: String,
    val content: String,
    val type: CommunityType,
    val image: List<String>? = null,
    val user: UserResponse,
    val createdAt: LocalDateTime
)

data class PostsResponse(
    val posts: List<PostDetailResponse>
)

data class PostIdResponse(
    val postId: Long
)

class PostResponse(
    val postId: Long,
    val title: String,
    val content: String,
    val type: CommunityType,
    val user: UserResponse,
    val createdAt: LocalDateTime
)
