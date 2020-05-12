package me.xfl03.sew.entity

import me.xfl03.framework.view.Name
import me.xfl03.framework.view.NumberAdapter
import me.xfl03.framework.view.Order

import javax.persistence.*

@Entity
data class CourseSelect(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("选课ID")
    @Order(1)
    var id: Long = 0,
    @Column
    @Name("学生")
    @NumberAdapter("student")
    @Order(3)
    var studentId: Long = 0,
    @Column
    @Name("课程")
    @NumberAdapter("course")
    @Order(2)
    var courseId: Long = 0,
    @Column
    @Name("分数")
    @Order(4)
    @NumberAdapter("score")
    var score: Int = 0
)