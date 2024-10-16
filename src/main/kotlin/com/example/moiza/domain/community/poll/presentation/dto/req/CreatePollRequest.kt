package com.example.moiza.domain.community.poll.presentation.dto.req

data class CreatePollRequest(
    val title: String,
    val content: String,
    val options: List<PollOptionRequest>,
    val hashtags: List<String>
)

data class PollOptionRequest(
    val description: String
)
