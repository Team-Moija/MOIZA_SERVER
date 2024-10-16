package com.example.moiza.domain.community.poll.service

import com.example.moiza.domain.community.community.domain.entity.CommunityHashtag
import com.example.moiza.domain.community.community.domain.entity.Hashtag
import com.example.moiza.domain.community.community.domain.entity.type.CommunityType
import com.example.moiza.domain.community.community.domain.repository.CommunityHashtagRepository
import com.example.moiza.domain.community.community.domain.repository.HashtagRepository
import com.example.moiza.domain.community.poll.domain.entity.Poll
import com.example.moiza.domain.community.poll.domain.entity.PollOption
import com.example.moiza.domain.community.poll.domain.repository.PollRepository
import com.example.moiza.domain.community.poll.presentation.dto.req.CreatePollRequest
import com.example.moiza.domain.community.poll.presentation.dto.res.PollIdResponse
import com.example.moiza.domain.user.facade.UserFacade
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class CreatePollService(
    private val pollRepository: PollRepository,
    private val userFacade: UserFacade,
    private val hashtagRepository: HashtagRepository,
    private val communityHashtagRepository: CommunityHashtagRepository
) {

    fun execute(request: CreatePollRequest): PollIdResponse {
        val user = userFacade.getCurrentUser()
        val poll = Poll(
            title = request.title,
            content = request.content,
            user = user
        )

        val pollOptions = request.options.map { optionRequest ->
            PollOption(optionRequest.description, poll)
        }

        poll.addOptions(pollOptions)
        pollRepository.save(poll)

        if (request.hashtags.isNotEmpty()) {
            request.hashtags.forEach { name ->
                val hashtag = hashtagRepository.findByName(name) ?: hashtagRepository.save(Hashtag(name))

                communityHashtagRepository.save(CommunityHashtag(
                    communityId = poll.id,
                    communityType = CommunityType.POLL,
                    hashtag = hashtag
                ))
            }
        }
        return PollIdResponse(poll.id)
    }
}
