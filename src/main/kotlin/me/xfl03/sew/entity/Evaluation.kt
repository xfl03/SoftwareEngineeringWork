package me.xfl03.sew.entity

import javax.persistence.*

@Entity
data class Evaluation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column
    var courseId: Long = 0,
    @Column
    var studentId: Long = 0,
    @Column
    var comment: String = ""
)