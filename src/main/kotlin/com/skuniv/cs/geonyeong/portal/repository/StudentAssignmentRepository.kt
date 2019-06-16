package com.skuniv.cs.geonyeong.portal.repository

import com.skuniv.cs.geonyeong.portal.domain.entity.StudentAssignment
import org.springframework.data.jpa.repository.JpaRepository

interface StudentAssignmentRepository: JpaRepository<StudentAssignment,Long>, StudentAssignmentRepositoryCustom