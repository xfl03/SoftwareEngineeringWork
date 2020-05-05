package me.xfl03.sew.entity

import me.xfl03.framework.view.Name

import javax.persistence.*

@Entity
data class CourseSelect(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("ѡ��ID")
    var id: Long = 0,
    @Column
    @Name("ѧ��ID")
    var studentId: Long = 0,
    @Column
    @Name("�γ�ID")
    var courseId: Long = 0
)