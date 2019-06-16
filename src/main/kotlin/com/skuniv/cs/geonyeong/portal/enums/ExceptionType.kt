package com.skuniv.cs.geonyeong.portal.enums

import com.skuniv.cs.geonyeong.portal.domain.vo.ExceptionResponse
import com.skuniv.cs.geonyeong.portal.exception.MissingAccountTypeException
import com.skuniv.cs.geonyeong.portal.exception.SignInException
import com.skuniv.cs.geonyeong.portal.exception.TokenExpireException
import com.skuniv.cs.geonyeong.portal.exception.TokenInvalidException
import org.apache.commons.lang3.StringUtils
import java.lang.RuntimeException

enum class ExceptionType(
        val exception: Class<out Exception>,
        val status: Int,
        val message: String
) {
    SIGNIN_EXCEPTION(SignInException::class.java, 80801, "Sign In Exception"),
    TOKEN_EXPIRE_EXCEPTION(TokenExpireException::class.java, 80802, "Token Expire"),
    TOKEN_INVALID_EXCEPTION(TokenInvalidException::class.java, 80803, "Token Invalid"),
    MISS_ACCOUNT_TYPE_EXCEPTION(MissingAccountTypeException::class.java, 10001, "request header's missing Account-Type Key")
    ;

    companion object {
        fun getExceptionResponse(exception: RuntimeException) : ExceptionResponse? {
            ExceptionType.values().forEach {
                if(StringUtils.equals(exception.javaClass.name, it.exception.javaClass.name)) {
                    return ExceptionResponse(status = it.status, message = it.message)
                }
            }
            return null
        }
    }
}