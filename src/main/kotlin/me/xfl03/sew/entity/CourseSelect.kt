package me.xfl03.sew.entity

import javax.persistence.*

@Entity
data class CourseSelect(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column
    var studentId: Long = 0,
    @Column
    var teacherId: Long = 0,
    @Column
    var courseId: Long = 0
)