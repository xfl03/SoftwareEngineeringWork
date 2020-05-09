package me.xfl03.sew.entity

import me.xfl03.framework.view.Name
import me.xfl03.framework.view.NumberAdapter
import java.sql.Date
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class Exam(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("����ID")
    var id:Long,
    @Column
    @Name("�γ�")
    @NumberAdapter("course")
    var courseId:Long,
    @Column
    @Name("����")
    var date:Date,
    @Column
    @Name("�γ�")
    @NumberAdapter("examTime")
    var time:Int
)