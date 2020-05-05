package me.xfl03.sew.repository

import me.xfl03.framework.repo.Repo
import me.xfl03.framework.repo.Repository
import me.xfl03.sew.entity.Student

@Repo
interface StudentRepo : Repository<Student> {
    fun findByIdAndPassword(id: Long, password: String): Student?
}