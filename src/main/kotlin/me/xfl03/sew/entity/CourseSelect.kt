package me.xfl03.sew.entity

import me.xfl03.framework.view.Name
import me.xfl03.framework.view.NumberAdapter
import me.xfl03.framework.view.Order

import javax.persistence.*

@Entity
data class CourseSelect(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("ѡ��ID")
    @Order(1)
    var id: Long = 0,
    @Column
    @Name("ѧ��")
    @NumberAdapter("student")
    @Order(3)
    var studentId: Long = 0,
    @Column
    @Name("�γ�")
    @NumberAdapter("course")
    @Order(2)
    var courseId: Long = 0,
    @Column
    @Name("����")
    @Order(4)
    @NumberAdapter("score")
    var score: Int = 0
)