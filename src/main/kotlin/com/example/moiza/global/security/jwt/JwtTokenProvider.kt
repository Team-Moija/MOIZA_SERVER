package com.example.moiza.global.security.jwt

import com.example.moiza.domain.auth.domain.RefreshToken
import com.example.moiza.domain.auth.domain.repository.RefreshTokenRepository
import com.example.moiza.global.config.properties.JwtProperties
import com.example.moiza.global.security.principal.UserDetailsService
import com.teaminsert.homepage.global.security.jwt.exception.ExpiredJwtException
import com.teaminsert.homepage.global.security.jwt.exception.InvalidJwtException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val authDetailsService: UserDetailsService,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    private val ACCESS_KEY: String = "access_token"
    private val REFRESH_KEY: String = "refresh_token"

    fun createAccessToken(email: String): String
            = createToken(email, ACCESS_KEY, jwtProperties.accessTime)

    fun createRefreshToken(email: String): String {
        val token: String = createToken(email, REFRESH_KEY, jwtProperties.refreshTime)
        refreshTokenRepository.save(RefreshToken(token, email))
        return token
    }

    fun createToken(email: String, type: String, time: Long): String {
        val now = Date()
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
            .setSubject(email)
            .setHeaderParam("typ", type)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + time))
            .compact()
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearer: String? = request.getHeader("Authorization")
        return parseToken(bearer)
    }

    fun parseToken(bearerToken: String?): String? {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "")
        }
        return null
    }

    fun authorization(token: String): UsernamePasswordAuthenticationToken {
        val userDetails: UserDetails = authDetailsService.loadUserByUsername(getTokenSubject(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getTokenSubject(subject: String):String
            = getTokenBody(subject).body.subject

    fun getTokenBody(token: String): Jws<Claims> {
        try {
            return Jwts.parser().setSigningKey(jwtProperties.secretKey)
                .parseClaimsJws(token)
        } catch (e: io.jsonwebtoken.ExpiredJwtException) {
            throw ExpiredJwtException
        } catch (e: Exception) {
            throw InvalidJwtException
        }
    }
}