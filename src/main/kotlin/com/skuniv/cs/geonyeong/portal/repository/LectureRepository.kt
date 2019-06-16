package com.skuniv.cs.geonyeong.portal.repository

import com.skuniv.cs.geonyeong.portal.domain.entity.Lecture
import com.skuniv.cs.geonyeong.portal.domain.entity.Professor
import com.skuniv.cs.geonyeong.portal.domain.entity.Semester
import org.springframework.data.jpa.repository.JpaRepository

interface LectureRepository : JpaRepository<Lecture, Long> {
    fun findBySemesterAndProfessor(semester: Semester, professor: Professor) : List<Lecture>
}