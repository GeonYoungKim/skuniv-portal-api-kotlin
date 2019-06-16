package com.skuniv.cs.geonyeong.portal.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "lecture")
data class Lecture(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        var name: String,
        var lectureDay: String,
        var lectureTime: Double,
        var score: Double,
        @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinTable(name = "student_lecture", joinColumns = [JoinColumn(name = "lecture_id")], inverseJoinColumns = [JoinColumn(name = "student_id")])
        @JsonIgnore
        var studentList: List<Student>? = ArrayList<Student>(),

        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "professor_id")
        @JsonIgnore
        var professor: Professor,
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "semester_id")
        @JsonIgnore
        var semester: Semester,
        @OneToMany(mappedBy = "lecture", cascade = [CascadeType.ALL])
        @JsonIgnore
        var lectureDetailList: ArrayList<LectureDetail> = ArrayList<LectureDetail>(),
        @OneToMany(mappedBy = "lecture", cascade = [CascadeType.ALL])
        var assignmentList: ArrayList<Assignment> = ArrayList<Assignment>()
) {
    fun addLectureDetail(lectureDetail: LectureDetail) {
        lectureDetail.lecture = this
        this.lectureDetailList.add(lectureDetail)
    }

    fun addAssignment(assignment: Assignment) {
        assignment.lecture = this
        this.assignmentList.add(assignment)
    }
}