package com.skuniv.cs.geonyeong.portal.controller

import com.skuniv.cs.geonyeong.portal.constant.PortalConstant.Companion.PROFESSOR_ID_KEY
import com.skuniv.cs.geonyeong.portal.domain.entity.Assignment
import com.skuniv.cs.geonyeong.portal.domain.entity.Lecture
import com.skuniv.cs.geonyeong.portal.service.ProfessorService
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/portal/professor")
class ProfessorController(
        val professorService: ProfessorService
) {
    @RequestMapping(value = ["/semester/{semesterId}/lecture"], method = [RequestMethod.GET])
    fun getLectures(@PathVariable(value = "semesterId") semesterId: Long,
                    @RequestAttribute(name = PROFESSOR_ID_KEY) professorId: String
    ) = professorService.getLectures(semesterId, professorId);

    @RequestMapping(value = ["/semester/{semesterId}/lecture"], method = [RequestMethod.POST])
    fun createLecture(
            @PathVariable(value = "semesterId") semesterId: Long,
            @RequestAttribute(name = PROFESSOR_ID_KEY) professorId: String,
            @RequestBody lecture: Lecture
    ) = professorService.createLecture(semesterId, professorId, lecture);

    @RequestMapping(value = ["/lecture/{lectureId}"], method = [RequestMethod.GET])
    fun getLectureDetails(
            @PathVariable(value = "lectureId") lectureId: Long
    ) = professorService.getLectureDetails(lectureId)

    @RequestMapping(value = ["/lecture/detail/{lectureDetailId}"], method = [RequestMethod.PUT])
    fun updateLectureDetailCanceled(
            @PathVariable(value = "lectureDetailId") lectureDetailId: Long
    ) = professorService.updateLectureDetailCanceled(lectureDetailId)

    @RequestMapping(value = ["/lecture/{lectureId}/assignment"], method = [RequestMethod.POST])
    fun createLectureAssignment(
            @PathVariable(value = "lectureId") lectureId: Long
            , @RequestBody assignment: Assignment
    ) = professorService.createLectureAssignment(lectureId, assignment)

    @RequestMapping(value = ["/lecture/{lectureId}/assignment"], method = [RequestMethod.GET])
    fun getLectureAssignments(
            @PathVariable(value = "lectureId") lectureId: Long
    ) = professorService.getLectureAssignments(lectureId)

    @RequestMapping(value = ["/lecture/assignment/{assignmentId}"], method = [RequestMethod.GET])
    fun getProfessorAssignmentDetail(
            @PathVariable(value = "assignmentId") assignmentId: Long
    ) = professorService.getProfessorAssignmentDetail(assignmentId)
}