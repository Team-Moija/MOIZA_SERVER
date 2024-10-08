package com.example.moiza.domain.auth.service

import com.example.moiza.domain.auth.domain.RefreshToken
import com.example.moiza.domain.auth.domain.repository.RefreshTokenRepository
import com.example.moiza.domain.auth.exception.TokenNotFoundException
import com.example.moiza.domain.auth.presentation.dto.res.AccessTokenResponse
import com.example.moiza.global.security.jwt.JwtTokenProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReissueService(
        private val refreshTokenRepository: RefreshTokenRepository,
        private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional(readOnly = true)
    fun execute(token: String): AccessTokenResponse {
        val refreshToken: RefreshToken = getRefreshToken(token)
        return AccessTokenResponse(
                jwtTokenProvider.createAccessToken(refreshToken.email))
    }

    fun getRefreshToken(token: String)
        = refreshTokenRepository.findByIdOrNull(token)
            ?: throw TokenNotFoundException
}