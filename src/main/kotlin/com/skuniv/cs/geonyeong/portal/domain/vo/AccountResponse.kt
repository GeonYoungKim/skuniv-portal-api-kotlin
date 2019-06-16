package com.skuniv.cs.geonyeong.portal.domain.vo

import com.skuniv.cs.geonyeong.portal.enums.AccountType

data class AccountResponse (
        var token: String,
        var accountType: AccountType,
        var name: String
)