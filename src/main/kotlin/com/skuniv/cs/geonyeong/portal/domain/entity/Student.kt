package com.skuniv.cs.geonyeong.portal.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "student", indexes = [Index(name = "IPK_PHONE", columnList = "phone", unique = true), Index(name = "IPK_EMAIL", columnList = "email", unique = true)])
data class Student(
        @Id
        private var id: String,
        private var password: String,
        private var name: String,
        private var phone: String,
        private var email: String,
        @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinTable(name = "student_assignment", joinColumns = [JoinColumn(name = "student_id")], inverseJoinColumns = [JoinColumn(name = "assignment_id")])
        @JsonIgnore
        private var assignmentList: List<Assignment> = ArrayList<Assignment>(),
        @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinTable(name = "student_lecture", joinColumns = [JoinColumn(name = "student_id")], inverseJoinColumns = [JoinColumn(name = "lecture_id")])
        @JsonIgnore
        private var lectureList: List<Lecture> = ArrayList<Lecture>()
)