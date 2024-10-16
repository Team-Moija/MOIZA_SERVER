package com.example.moiza.domain.community.post.service

import com.example.moiza.domain.community.community.domain.entity.CommunityHashtag
import com.example.moiza.domain.community.community.domain.entity.Hashtag
import com.example.moiza.domain.community.community.domain.entity.type.CommunityType
import com.example.moiza.domain.community.community.domain.repository.CommunityHashtagRepository
import com.example.moiza.domain.community.community.domain.repository.HashtagRepository
import com.example.moiza.domain.community.post.domain.entity.Post
import com.example.moiza.domain.community.post.domain.repository.PostRepository
import com.example.moiza.domain.community.post.presentation.dto.req.CreatePostRequest
import com.example.moiza.domain.community.post.presentation.dto.res.PostIdResponse
import com.example.moiza.domain.user.facade.UserFacade
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class CreatePostService(
    private val postRepository: PostRepository,
    private val userFacade: UserFacade,
    private val hashtagRepository: HashtagRepository,
    private val communityHashtagRepository: CommunityHashtagRepository
) {

    fun execute(request: CreatePostRequest): PostIdResponse {
        val user = userFacade.getCurrentUser()
        val post = Post(
            title = request.title,
            content = request.content,
            user = user,
            image = request.image
        )

        postRepository.save(post)

        if (request.hashtags.isNotEmpty()) {
            request.hashtags.forEach { name ->
                val hashtag = hashtagRepository.findByName(name) ?: hashtagRepository.save(Hashtag(name))

                communityHashtagRepository.save(CommunityHashtag(
                    communityId = post.id,
                    communityType = CommunityType.POST,
                    hashtag = hashtag
                ))
            }
        }
        return PostIdResponse(post.id)
    }
}
