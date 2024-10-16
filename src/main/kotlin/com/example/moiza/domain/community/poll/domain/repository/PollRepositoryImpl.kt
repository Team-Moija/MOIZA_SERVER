package com.example.moiza.domain.community.poll.domain.repository

import com.example.moiza.domain.community.poll.domain.entity.QPoll.poll
import com.example.moiza.domain.community.poll.domain.entity.QPollOption.pollOption
import com.example.moiza.domain.community.poll.presentation.dto.res.PollOptionResponse
import com.example.moiza.domain.community.poll.presentation.dto.res.PollResponse
import com.example.moiza.domain.user.presentation.dto.res.UserResponse
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class PollRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : PollRepositoryCustom {

    override fun findAllPolls(): List<PollResponse> {
        return queryFactory
            .selectFrom(poll)
            .leftJoin(poll.options, pollOption).fetchJoin()
            .leftJoin(poll.user).fetchJoin()
            .fetch()
            .map { pollEntity ->
                val totalVotes = pollEntity.options.sumOf { it.votes.size }

                val options = pollEntity.options.map { option ->
                    PollOptionResponse(
                        pollOptionId = option.id,
                        description = option.description,
                        voteCount = option.votes.size,
                        votePercentage = if (totalVotes > 0) (option.votes.size.toDouble() / totalVotes) * 100 else 0.0
                    )
                }

                PollResponse(
                    pollId = pollEntity.id,
                    title = pollEntity.title,
                    content = pollEntity.content,
                    type = pollEntity.type,
                    options = options,
                    user = UserResponse(
                        nickname = pollEntity.user.nickname,
                        profile = pollEntity.user.profile
                    ),
                    createdAt = pollEntity.createdAt
                )
            }
    }
}
