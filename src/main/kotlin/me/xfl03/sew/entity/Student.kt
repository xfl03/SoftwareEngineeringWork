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
    @Name("ѧ��ID")
    var id: Long = 0,
    @Column
    @Name("����")
    var name: String = "",
    @Column
    @Name("�Ա�")
    @BooleanAdapter(t = "��", f = "Ů")
    var gender: Boolean = false,
    @Column
    @Name("רҵ")
    var major: String = "",
    @Column
    @Name("����")
    var department: Int = 0,
    @Column
    @Name("����")
    var birthday: Date = Date(System.currentTimeMillis()),
    @Column
    @Name("�ֻ�")
    var phone: Long = 0,
    @Column
    @Name("��ַ")
    var address: String = "",
    @Column
    @Name("����")
    var password: String = ""
) : Loginable