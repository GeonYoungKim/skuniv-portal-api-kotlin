package com.skuniv.cs.geonyeong.portal.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.skuniv.cs.geonyeong.portal.constant.PortalConstant.Companion.DAY_OF_WEEK_MAP
import com.skuniv.cs.geonyeong.portal.domain.entity.Assignment
import com.skuniv.cs.geonyeong.portal.domain.entity.Lecture
import com.skuniv.cs.geonyeong.portal.domain.entity.LectureDetail
import com.skuniv.cs.geonyeong.portal.domain.vo.LectureDay
import com.skuniv.cs.geonyeong.portal.repository.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProfessorService(
        val professorRepository: ProfessorRepository,
        val semesterRepository: SemesterRepository,
        val lectureRepository: LectureRepository,
        val lectureDetailRepository: LectureDetailRepository,
        val assignmentRepository: AssignmentRepository,
        val studentAssignmentRepository: StudentAssignmentRepository
) {
    companion object {
        val gson = Gson()
    }

    val listType = object : TypeToken<ArrayList<LectureDay>>() {}.rawType

    fun getLectures(semesterId: Long, professorId: String): List<Lecture> {
        val professor = professorRepository.findById(professorId).get()
        val semester = semesterRepository.findById(semesterId).get()
        val lectureList = lectureRepository
                .findBySemesterAndProfessor(semester, professor)
        lectureList.forEach {
            val lectureDetailList = gson.fromJson<List<LectureDay>>(it.lectureDay, listType)
            val dayNumList = lectureDetailList.map { DAY_OF_WEEK_MAP.get(it.lectureDay) }
            Collections.sort<Int>(dayNumList)
            val sortedLectureDay = dayNumList.map { DAY_OF_WEEK_MAP.inverse().get(it) }
                    .joinToString(",")
            it.lectureDay = sortedLectureDay
        }
        return lectureList
    }

    fun createLecture(semesterId: Long, professorId: String, lecture: Lecture): Lecture {
        val semester = semesterRepository.findById(semesterId).get();
        val professor = professorRepository.findById(professorId).get();
        professor.addLecture(lecture)
        val diffDays = makeDiffDays(semester.startDate, semester.endDate);
        val dayNumList = makeDayNumList(lecture.lectureDay);
        addLectureDetailList(diffDays, dayNumList, lecture, semester.startDate)
        semester.addLecture(lecture)
        semesterRepository.save(semester)
        return lecture
    }

    private fun addLectureDetailList(diffDays: Long, dayNumList: List<Int?>, lecture: Lecture, startDate: Date) {
        val lectureDetailList = gson.fromJson<List<LectureDay>>(lecture.lectureDay, listType);
        val cal = Calendar.getInstance();
        cal.time = startDate

        for (i in 1..diffDays) {
            cal.add(Calendar.DATE, 1)
            val dayNum = cal.get(Calendar.DAY_OF_WEEK);
            if (dayNumList.contains(dayNum)) {
                val lectureDay = lectureDetailList
                        .filter { DAY_OF_WEEK_MAP[it.lectureDay] == dayNum }
                        .first()
                val lectureDetail = LectureDetail(
                        canceled = false,
                        lecture = lecture,
                        lectureDetailTime = lectureDay.detailTime,
                        lectureDay = DAY_OF_WEEK_MAP.inverse().get(dayNum)!!,
                        lectureDate = Date(cal.timeInMillis)
                )
                lecture.addLectureDetail(lectureDetail)
            }
        }
    }

    private fun makeDayNumList(lectureDay: String): List<Int?> {
        val lectureDetailList = gson.fromJson<List<LectureDay>>(lectureDay, listType);
        return lectureDetailList
                .map { DAY_OF_WEEK_MAP[it.lectureDay] }
    }

    private fun makeDiffDays(startDate: Date, endDate: Date): Long {
        val diff = endDate.time - startDate.time
        return Math.abs(diff / (24 * 60 * 60 * 1000))
    }

    fun getLectureDetails(lectureId: Long): List<LectureDetail> {
        val lecture = lectureRepository.findById(lectureId).get()
        return lectureDetailRepository.findByLecture(lecture)
    }

    fun updateLectureDetailCanceled(lectureDetailId: Long): LectureDetail {
        val lectureDetail = lectureDetailRepository.findById(lectureDetailId).get();
        lectureDetail.canceled = !lectureDetail.canceled
        return lectureDetailRepository.save(lectureDetail)
    }

    fun createLectureAssignment(lectureId: Long, assignment: Assignment): Assignment {
        val lecture = lectureRepository.findById(lectureId).get();
        lecture.addAssignment(assignment)
        lectureRepository.save(lecture)
        return assignment
    }

    fun getLectureAssignments(lectureId: Long) = assignmentRepository.findByLecture(lectureRepository.findById(lectureId).get())

    fun getProfessorAssignmentDetail(assignmentId: Long) =  studentAssignmentRepository.findByAssignmentId(assignmentId);
}