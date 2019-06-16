package com.skuniv.cs.geonyeong.portal.service

import com.skuniv.cs.geonyeong.portal.domain.entity.Professor
import com.skuniv.cs.geonyeong.portal.domain.vo.AccountResponse
import com.skuniv.cs.geonyeong.portal.enums.AccountType
import com.skuniv.cs.geonyeong.portal.exception.SignInException
import com.skuniv.cs.geonyeong.portal.repository.ProfessorRepository
import org.springframework.stereotype.Service

@Service
class AccountService(
        val professorRepository: ProfessorRepository,
        val cryptorService: CryptorService,
        val jwtService: JwtService
) {

    fun signUp(professor: Professor): Professor {
        val password = professor.password
        professor.password = cryptorService.encryptBase64(password)
        return professorRepository.save(professor)
    }

    fun signIn(professor: Professor) : AccountResponse {
        val password = cryptorService.encryptBase64(professor.password)
        val findProfessor = professorRepository.findByIdAndPassword(id = professor.id, password = password)
        if(findProfessor != null) {
            val token = jwtService.makeJwt(findProfessor.id, findProfessor.password)
            return AccountResponse(token = token, name = findProfessor.name, accountType = AccountType.PROFESSOR)
        }
        throw SignInException()
    }
}