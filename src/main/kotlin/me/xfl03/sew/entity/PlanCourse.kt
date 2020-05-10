package me.xfl03.sew.entity

import javax.persistence.*

@Entity
data class PlanCourse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column
    var planId: Long = 0,
    @Column
    var courseId: Long = 0
)