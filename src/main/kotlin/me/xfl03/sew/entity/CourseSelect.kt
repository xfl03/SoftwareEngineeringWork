package me.xfl03.sew.entity

import me.xfl03.framework.view.Name
import me.xfl03.framework.view.NumberAdapter

import javax.persistence.*

@Entity
data class CourseSelect(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("选课ID")
    var id: Long = 0,
    @Column
    @Name("学生ID")
    var studentId: Long = 0,
    @Column
    @Name("课程ID")
    var courseId: Long = 0,
    @Column
    @Name("分数")
    @NumberAdapter("score")
    var score: Int = 0
)