package com.skuniv.cs.geonyeong.portal.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "lecture_detail")
data class LectureDetail (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "lecture_date")
        var lectureDate: Date,
        @Column(name = "lecture_day")
        var lectureDay: String,
        @Column(name = "lecture_detail_time")
        var lectureDetailTime: Double,
        var canceled: Boolean,
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "lecture_id")
        @JsonIgnore
        var lecture: Lecture
)