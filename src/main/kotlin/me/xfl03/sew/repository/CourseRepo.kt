package me.xfl03.sew.repository

import me.xfl03.framework.repo.Repo
import me.xfl03.framework.repo.Repository
import me.xfl03.sew.entity.Course

@Repo
interface CourseRepo : Repository<Course> {
    fun listByNameContains(name: String): List<Course>
    fun listByIdIn(ids: List<Long>): List<Course>
    fun listByTeacherid(teacherId: Long): List<Course>
}