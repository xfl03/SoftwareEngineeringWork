package me.xfl03.sew.repository

import me.xfl03.framework.repo.Repo
import me.xfl03.framework.repo.Repository
import me.xfl03.sew.entity.Exam

@Repo
interface ExamRepo : Repository<Exam> {
    fun listByCourseidIn(courseId: List<Long>):List<Exam>
}