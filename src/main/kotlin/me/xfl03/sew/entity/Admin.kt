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
    @Name("π‹¿Ì‘±ID")
    var id: Long = 0,
    @Column
    @Name("√‹¬Î")
    @Order(3)
    var password: String = ""
) : Loginable