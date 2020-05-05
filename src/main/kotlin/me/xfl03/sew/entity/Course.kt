package me.xfl03.sew.entity

import me.xfl03.framework.view.Name

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Course(
    @Id
    @Name("�γ�ID")
    var id: Long = 0,
    @Column
    @Name("�γ���")
    var name: String = "",
    @Column
    @Name("��ʦID")
    var teacherId: Long = 0,
    @Column
    @Name("�γ�����")
    var number: Int = 0
)