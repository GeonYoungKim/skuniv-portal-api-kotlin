package com.skuniv.cs.geonyeong.portal.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList

@Entity
@Table(name = "semester")
data class Semester(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var name: String,
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "start_date")
        var startDate: Date,
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "end_date")
        var endDate: Date,
        @OneToMany(mappedBy = "semester", cascade = [CascadeType.ALL])
        @JsonIgnore
        var lectureList: ArrayList<Lecture> = ArrayList<Lecture>()
) {
    fun addLecture(lecture: Lecture) {
        lecture.semester = this
        this.lectureList.add(lecture)
    }
}