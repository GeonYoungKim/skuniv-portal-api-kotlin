package com.skuniv.cs.geonyeong.portal.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "professor", indexes = [Index(name = "IPK_PHONE", columnList = "phone"), Index(name = "IPK_EMAIL", columnList = "email")])
data class Professor(
        @Id
        var id: String,
        var password: String,
        var name: String,
        var phone: String,
        var email: String,
        @OneToMany(mappedBy = "professor", cascade = [CascadeType.ALL])
        @JsonIgnore
        var lectureList: ArrayList<Lecture> = ArrayList<Lecture>()
) {
    fun addLecture(lecture: Lecture) {
        lecture.professor = this
        lectureList.add(lecture)
    }
}