package me.xfl03.sew.repository

import me.xfl03.framework.repo.Repo
import me.xfl03.framework.repo.Repository
import me.xfl03.sew.entity.Evaluation

@Repo
interface EvaluationRepo : Repository<Evaluation> {
    fun listByCourseid(studentId: Long): List<Evaluation>
    fun findByStudentidAndCourseid(studentId: Long, courseId: Long): Evaluation?
}