package me.xfl03.sew.entity

import me.xfl03.framework.view.BooleanAdapter
import me.xfl03.framework.view.Name

import java.sql.Date

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Student(
    @Id
    @Name("学生ID")
    var id: Long = 0,
    @Column
    @Name("姓名")
    var name: String = "",
    @Column
    @Name("性别")
    @BooleanAdapter(t = "男", f = "女")
    var gender: Boolean = false,
    @Column
    @Name("专业")
    var major: String = "",
    @Column
    @Name("宿舍")
    var department: Int = 0,
    @Column
    @Name("生日")
    var birthday: Date = Date(System.currentTimeMillis()),
    @Column
    @Name("手机")
    var phone: Long = 0,
    @Column
    @Name("地址")
    var address: String = "",
    @Column
    @Name("密码")
    var password: String = ""
) : Loginable