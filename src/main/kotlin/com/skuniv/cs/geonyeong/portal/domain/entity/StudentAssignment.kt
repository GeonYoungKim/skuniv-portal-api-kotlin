package com.skuniv.cs.geonyeong.portal.domain.entity

import javax.persistence.*

@Entity
@Table(name = "student_assignment")
data class StudentAssignment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private var id: Long,
        private var part: String,
        @Column(name = "student_id")
        private var studentId: String,
        @Column(name = "assignment_id")
        private var assignmentId: Long
)