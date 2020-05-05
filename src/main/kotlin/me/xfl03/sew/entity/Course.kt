package me.xfl03.sew.entity

import me.xfl03.framework.view.Name

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Course(
    @Id
    @Name("课程ID")
    var id: Long = 0,
    @Column
    @Name("课程名")
    var name: String = "",
    @Column
    @Name("教师ID")
    var teacherId: Long = 0,
    @Column
    @Name("课程人数")
    var number: Int = 0
)