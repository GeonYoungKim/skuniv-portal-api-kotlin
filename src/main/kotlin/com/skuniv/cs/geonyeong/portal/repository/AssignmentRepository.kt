package com.skuniv.cs.geonyeong.portal.repository

import com.skuniv.cs.geonyeong.portal.domain.entity.Assignment
import com.skuniv.cs.geonyeong.portal.domain.entity.Lecture
import org.springframework.data.jpa.repository.JpaRepository

interface AssignmentRepository : JpaRepository<Assignment, Long> {
    fun findByLecture(lecture: Lecture): List<Assignment>
}