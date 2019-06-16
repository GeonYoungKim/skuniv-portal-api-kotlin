package com.skuniv.cs.geonyeong.portal.domain.entity

import javax.persistence.*

@Entity
@Table(name = "student_lecture")
data class StudentLecture (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private var id: Long 
)