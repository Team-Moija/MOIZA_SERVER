package com.example.moiza.domain.community.post.domain.repository

import com.example.moiza.domain.community.post.domain.entity.Post
import com.example.moiza.domain.community.post.domain.entity.QPost.post
import com.example.moiza.domain.community.post.presentation.dto.res.PostResponse
import com.example.moiza.domain.user.presentation.dto.res.UserResponse
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class PostRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : PostRepositoryCustom {

    override fun findById(postId: Long): Post? {
        return queryFactory
            .selectFrom(post)
            .leftJoin(post.image)
            .fetchJoin()
            .where(post.id.eq(postId))
            .fetchOne()
    }

    override fun findAllPosts(): List<PostResponse> {
        return queryFactory
            .select(
                Projections.constructor(
                    PostResponse::class.java,
                    post.id,
                    post.title,
                    post.content,
                    post.type,
                    Projections.constructor(
                        UserResponse::class.java,
                        post.user.nickname,
                        post.user.profile
                    ),
                    post.createdAt
                )
            )
            .from(post)
            .orderBy(post.createdAt.desc())
            .fetch()
    }
}
