package com.skuniv.cs.geonyeong.portal.interceptor

import com.skuniv.cs.geonyeong.portal.constant.PortalConstant.Companion.PROFESSOR_ID_KEY
import com.skuniv.cs.geonyeong.portal.constant.PortalConstant.Companion.STUDENT_ID_KEY
import com.skuniv.cs.geonyeong.portal.enums.AccountType
import com.skuniv.cs.geonyeong.portal.exception.MissingAccountTypeException
import com.skuniv.cs.geonyeong.portal.exception.TokenExpireException
import com.skuniv.cs.geonyeong.portal.exception.TokenInvalidException
import com.skuniv.cs.geonyeong.portal.service.JwtService
import io.jsonwebtoken.ExpiredJwtException
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Component
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class PortalInterceptor(
        val jwtService: JwtService
) : HandlerInterceptorAdapter() {

    val HEADER_TOKEN_KEY = "token"
    val HEADER_ACCOUNT_TYPE_KEY = "accountType"

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (StringUtils.equals(request.method, "OPTIONS"))
            return true

        try {
            val accountType = AccountType
                    .valueOf(request.getHeader(HEADER_ACCOUNT_TYPE_KEY))
            var isCheck = false
            val jwt: String
            when (accountType) {
                AccountType.PROFESSOR -> {
                    jwt = request.getHeader(HEADER_TOKEN_KEY)
                    isCheck = jwtService.checkProfessor(jwt)
                    checkAuthentication(isCheck, request, PROFESSOR_ID_KEY,
                            jwtService.getJwtId(jwt))
                }
                AccountType.STUDENT -> {
                    jwt = request.getHeader(HEADER_TOKEN_KEY)
                    isCheck = jwtService.checkStudent(jwt)
                    checkAuthentication(isCheck, request, STUDENT_ID_KEY,
                            jwtService.getJwtId(jwt))
                }
            }
        } catch (e: ExpiredJwtException) {
            throw TokenExpireException()
        } catch (e: NullPointerException) {
            throw MissingAccountTypeException()
        }
        return true
    }

    private fun checkAuthentication(isCheck: Boolean, request: HttpServletRequest, attributeKey: String, attributeValue: String) {
        if (isCheck) {
            request.setAttribute(attributeKey, attributeValue)
        } else {
            throw TokenInvalidException()
        }
    }
}