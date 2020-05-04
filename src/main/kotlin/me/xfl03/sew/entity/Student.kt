package me.xfl03.sew.entity

import java.sql.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Student(
    @Id
    var id: Long = 0,
    @Column
    var name: String = "",
    @Column
    var gender: Boolean = false,
    @Column
    var majorId: Int = 0,
    @Column
    var departmentId: Int = 0,
    @Column
    var birthday: Date = Date(System.currentTimeMillis()),
    @Column
    var phone: Long = 0,
    @Column
    var address: String = "",
    @Column
    var password: String = ""
)