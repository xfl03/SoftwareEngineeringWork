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
    @Name("考试ID")
    var id:Long,
    @Column
    @Name("课程")
    @NumberAdapter("course")
    var courseId:Long,
    @Column
    @Name("日期")
    var date:Date,
    @Column
    @Name("课程")
    @NumberAdapter("examTime")
    var time:Int
)