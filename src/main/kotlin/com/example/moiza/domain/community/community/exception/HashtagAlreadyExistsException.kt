package com.example.moiza.domain.community.community.exception

import com.example.moiza.global.error.exception.BusinessException
import com.example.moiza.global.error.exception.ErrorCode

object HashtagAlreadyExistsException: BusinessException(ErrorCode.HASHTAG_ALREADY_EXISTS)
