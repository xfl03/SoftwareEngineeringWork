package me.xfl03.sew.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Course(
    @Id
    var id: Long = 0,
    @Column
    var name: String = "",
    @Column
    var teacherId: Long = 0,
    @Column
    var number: Int = 0
)