package me.xfl03.sew.entity

import me.xfl03.framework.view.BooleanAdapter
import me.xfl03.framework.view.Name
import me.xfl03.framework.view.Order

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Admin(
    @Id
    @Name("管理员ID")
    var id: Long = 0,
    @Column
    @Name("名称")
    var name: String = "",
    @Column
    @Name("密码")
    @Order(3)
    var password: String = ""
) : Loginable