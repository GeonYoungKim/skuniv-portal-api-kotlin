package com.skuniv.cs.geonyeong.portal.repository

import com.skuniv.cs.geonyeong.portal.domain.vo.ProfessorAssignmentDetail

interface StudentAssignmentRepositoryCustom {
    fun findByAssignmentId(assignmentId: Long?): List<ProfessorAssignmentDetail>
}