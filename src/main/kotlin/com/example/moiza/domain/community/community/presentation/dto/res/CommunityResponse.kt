package com.example.moiza.domain.community.community.presentation.dto.res

import com.example.moiza.domain.community.community.domain.entity.type.CommunityType
import com.example.moiza.domain.community.poll.presentation.dto.res.PollOptionResponse
import com.example.moiza.domain.user.presentation.dto.res.UserResponse
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

data class CommunityResponse(
    val contents: List<CommunityItemResponse>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CommunityItemResponse(
    val id: Long,
    val title: String,
    val content: String,
    val type: CommunityType,
    val user: UserResponse,
    val createdAt: LocalDateTime,
    val options: List<PollOptionResponse>? = null
)
