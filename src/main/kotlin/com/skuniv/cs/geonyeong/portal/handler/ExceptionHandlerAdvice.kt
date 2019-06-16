package com.skuniv.cs.geonyeong.portal.handler

import com.skuniv.cs.geonyeong.portal.domain.vo.ExceptionResponse
import com.skuniv.cs.geonyeong.portal.enums.ExceptionType
import com.skuniv.cs.geonyeong.portal.exception.MissingAccountTypeException
import com.skuniv.cs.geonyeong.portal.exception.SignInException
import com.skuniv.cs.geonyeong.portal.exception.TokenExpireException
import com.skuniv.cs.geonyeong.portal.exception.TokenInvalidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackages = ["com.skuniv.cs.geonyeong.portal.controller"])
class ExceptionHandlerAdvice {

    private val DEFAULT_EXCEPTION_RESPONSE = ExceptionResponse(status = 80800, message = "Unknown Exception")

    @ExceptionHandler(value = [SignInException::class])
    fun handleParsingException(e: SignInException) = ExceptionType.getExceptionResponse(e)?: DEFAULT_EXCEPTION_RESPONSE

    @ExceptionHandler(value = [TokenExpireException::class])
    fun handleTokenExpireException(e: TokenExpireException) = ExceptionType.getExceptionResponse(e)?: DEFAULT_EXCEPTION_RESPONSE

    @ExceptionHandler(value = [MissingAccountTypeException::class])
    fun handleMissingAccountTypeException(e: MissingAccountTypeException) = ExceptionType.getExceptionResponse(e)?: DEFAULT_EXCEPTION_RESPONSE

    @ExceptionHandler(value = [TokenInvalidException::class])
    fun handleTokenInvalidExceptionException(e: TokenInvalidException) = ExceptionType.getExceptionResponse(e)?: DEFAULT_EXCEPTION_RESPONSE
}