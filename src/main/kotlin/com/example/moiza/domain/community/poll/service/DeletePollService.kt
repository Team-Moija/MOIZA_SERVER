package com.example.moiza.domain.community.poll.service

import com.example.moiza.domain.community.community.exception.AccessDeniedException
import com.example.moiza.domain.community.poll.domain.repository.PollRepository
import com.example.moiza.domain.community.poll.exception.PollNotFoundException
import com.example.moiza.domain.user.facade.UserFacade
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class DeletePollService(
    private val pollRepository: PollRepository,
    private val userFacade: UserFacade
) {

    fun execute(postId: Long) {
        val user = userFacade.getCurrentUser()

        val poll = pollRepository.findById(postId)
            .orElseThrow { PollNotFoundException }

        if (poll.user != user) {
            throw AccessDeniedException
        }

        pollRepository.delete(poll)
    }
}
