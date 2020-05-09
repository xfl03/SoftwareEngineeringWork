package me.xfl03.sew.entity

import me.xfl03.framework.view.Name
import me.xfl03.framework.view.NumberAdapter

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
    @Name("��ʦ")
    @NumberAdapter("teacher")
    var teacherId: Long = 0,
    @Column
    @Name("����")
    var number: Int = 0,
    @Column
    @Name("ʱ��")
    @NumberAdapter("time")
    var time: Int = 0,
    @Column
    @Name("ѧ��")
    var credit: Int = 0
)