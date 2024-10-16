package com.example.moiza.domain.community.post.presentation.dto.req

data class CreatePostRequest(
    val title: String,
    val content: String,
    val image: List<String>?,
    val hashtags: List<String>
)
