package com.skuniv.cs.geonyeong.portal.controller

import com.skuniv.cs.geonyeong.portal.domain.entity.Professor
import com.skuniv.cs.geonyeong.portal.service.AccountService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/portal/account")
class AccountController(
        val accountService: AccountService) {

    @RequestMapping(value = "/professor/signUp", method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun professorSignUp(@RequestBody professor: Professor) = accountService.signUp(professor)

    @RequestMapping(value = "/professor/signIn", method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun professorSignIn(@RequestBody professor: Professor) = accountService.signIn(professor)
}








