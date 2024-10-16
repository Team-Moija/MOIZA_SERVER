package com.example.moiza.domain.community.community.service

import com.example.moiza.domain.community.community.domain.entity.type.CommunityType
import com.example.moiza.domain.community.community.presentation.dto.res.CommunityItemResponse
import com.example.moiza.domain.community.community.presentation.dto.res.CommunityResponse
import com.example.moiza.domain.community.poll.domain.repository.PollRepositoryCustom
import com.example.moiza.domain.community.poll.presentation.dto.res.PollOptionResponse
import com.example.moiza.domain.community.post.domain.repository.PostRepositoryCustom
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QueryAllContentsService(
    private val postRepositoryCustom: PostRepositoryCustom,
    private val pollRepositoryCustom: PollRepositoryCustom
) {
    fun execute(): CommunityResponse {
        val posts = postRepositoryCustom.findAllPosts()
            .map { post ->
                CommunityItemResponse(
                    id = post.postId,
                    title = post.title,
                    content = post.content,
                    type = CommunityType.POST,
                    user = post.user,
                    createdAt = post.createdAt
                )
            }

        val polls = pollRepositoryCustom.findAllPolls()
            .map { poll ->
                CommunityItemResponse(
                    id = poll.pollId,
                    title = poll.title,
                    content = poll.content,
                    type = CommunityType.POLL,
                    user = poll.user,
                    createdAt = poll.createdAt,
                    options = poll.options.map { option ->
                        PollOptionResponse(
                            pollOptionId = option.pollOptionId,
                            description = option.description,
                            voteCount = option.voteCount,
                            votePercentage = if (option.voteCount > 0) {
                                (option.voteCount.toDouble() / option.voteCount) * 100
                            } else 0.0
                        )
                    }
                )
            }

        val contents = (posts + polls).sortedByDescending { it.createdAt }

        return CommunityResponse(contents = contents)
    }
}
