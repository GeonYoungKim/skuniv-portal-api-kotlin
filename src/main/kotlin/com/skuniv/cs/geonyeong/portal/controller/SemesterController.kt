package com.skuniv.cs.geonyeong.portal.controller

import com.skuniv.cs.geonyeong.portal.domain.entity.Semester
import com.skuniv.cs.geonyeong.portal.repository.SemesterRepository
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/portal/semester")
class SemesterController(
        val semesterRepository: SemesterRepository
) {
    companion object {
        @JvmStatic
        val log = LoggerFactory.getLogger(this::class.java)
        @JvmStatic
        val dt = SimpleDateFormat("yyyyMMdd")
    }

    private val START_DATE = "20190301"
    private val END_DATE = "20190620"

    private val START_DATE2 = "20190901"
    private val END_DATE2 = "20191220"

    @RequestMapping(value = "/", method = [RequestMethod.POST])
    fun craeteSemester() {
        var semester = Semester(name = "1학기", startDate = dt.parse(START_DATE), endDate = dt.parse(END_DATE))
        semesterRepository.save(semester)
        semester = Semester(name = "2학기", startDate = dt.parse(START_DATE2), endDate = dt.parse(END_DATE2))
        semesterRepository.save(semester)
        semesterRepository.flush()
    }

    @RequestMapping(value = "/", method = [RequestMethod.GET])
    fun getSemesters() = semesterRepository.findAll()
}