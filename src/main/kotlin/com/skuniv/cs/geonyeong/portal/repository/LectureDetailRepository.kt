package com.skuniv.cs.geonyeong.portal.repository

import com.skuniv.cs.geonyeong.portal.domain.entity.Lecture
import com.skuniv.cs.geonyeong.portal.domain.entity.LectureDetail
import org.springframework.data.jpa.repository.JpaRepository

interface LectureDetailRepository : JpaRepository<LectureDetail, Long> {
    fun findByLecture(lecture: Lecture) : List<LectureDetail>
}