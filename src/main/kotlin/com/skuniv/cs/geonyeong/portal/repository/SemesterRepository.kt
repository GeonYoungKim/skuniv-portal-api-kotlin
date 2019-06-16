package com.skuniv.cs.geonyeong.portal.repository

import com.skuniv.cs.geonyeong.portal.domain.entity.Semester
import org.springframework.data.jpa.repository.JpaRepository

interface SemesterRepository : JpaRepository<Semester, Long>