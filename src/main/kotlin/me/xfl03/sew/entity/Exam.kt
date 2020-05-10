package me.xfl03.sew.entity

import me.xfl03.framework.view.Name
import me.xfl03.framework.view.NumberAdapter
import java.sql.Date
import javax.persistence.*

@Entity
data class Exam(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("考试ID")
    var id: Long = 0,
    @Column
    @Name("课程")
    @NumberAdapter("course")
    var courseId: Long = 0,
    @Column
    @Name("日期")
    var date: Date = Date(System.currentTimeMillis()),
    @Column
    @Name("时间")
    @NumberAdapter("examTime")
    var time: Int = 0
)