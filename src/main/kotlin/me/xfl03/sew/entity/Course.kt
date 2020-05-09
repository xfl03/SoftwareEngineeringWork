package me.xfl03.sew.entity

import me.xfl03.framework.view.Name
import me.xfl03.framework.view.NumberAdapter

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
    @Name("教师")
    @NumberAdapter("teacher")
    var teacherId: Long = 0,
    @Column
    @Name("人数")
    var number: Int = 0,
    @Column
    @Name("时间")
    @NumberAdapter("time")
    var time: Int = 0,
    @Column
    @Name("学分")
    var credit: Int = 0
)