package me.xfl03.sew.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Plan(
    @Id
    var id: Long = 0,
    @Column
    var name: String = "",
    @Column
    var credits: Int = 0
)