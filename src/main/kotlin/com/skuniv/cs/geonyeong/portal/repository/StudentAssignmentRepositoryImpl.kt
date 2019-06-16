package com.skuniv.cs.geonyeong.portal.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.skuniv.cs.geonyeong.portal.domain.entity.QStudent.student
import com.skuniv.cs.geonyeong.portal.domain.entity.QStudentAssignment.studentAssignment
import com.skuniv.cs.geonyeong.portal.domain.vo.ProfessorAssignmentDetail

class StudentAssignmentRepositoryImpl(
        val queryFactory: JPAQueryFactory) : StudentAssignmentRepositoryCustom {
    override fun findByAssignmentId(assignmentId: Long?): List<ProfessorAssignmentDetail> {

        return queryFactory
                .select(Projections.fields(ProfessorAssignmentDetail::class.java,
                        student.name.`as`("name"),
                        studentAssignment.part.`as`("part"),
                        student.phone.`as`("phone")
                )).from(studentAssignment)
                .where(studentAssignment.assignmentId.eq(assignmentId))
                .join(student).on(studentAssignment.studentId.eq(student.id))
                .fetch()
    }
}