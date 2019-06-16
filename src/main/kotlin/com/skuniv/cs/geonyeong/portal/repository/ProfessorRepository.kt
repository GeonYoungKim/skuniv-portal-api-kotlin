package com.skuniv.cs.geonyeong.portal.repository

import com.skuniv.cs.geonyeong.portal.domain.entity.Professor
import org.springframework.data.jpa.repository.JpaRepository

interface ProfessorRepository: JpaRepository<Professor, String> {
    fun findByIdAndPassword(id: String, password: String): Professor?
}