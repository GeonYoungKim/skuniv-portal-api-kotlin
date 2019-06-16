package com.skuniv.cs.geonyeong.portal.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.skuniv.cs.geonyeong.portal.enums.AssignmentType
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "assignment", indexes = [Index(name = "IPK_TYPE_NAME", columnList = "type, name", unique = true)])
data class Assignment (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        @Column(name = "type", length = 100)
        @Enumerated(EnumType.STRING)
        var type: AssignmentType,
        @Column(length = 200)
        var name: String,
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "start_date")
        var startDate: Date,
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "end_date")
        var endDate: Date,
        @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinTable(name = "student_assignment", joinColumns = [JoinColumn(name = "assignment_id")], inverseJoinColumns = [JoinColumn(name = "student_id")])
        val studentList: ArrayList<Student>? = ArrayList<Student>(),
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "lecture_id")
        @JsonIgnore
        var lecture: Lecture
)