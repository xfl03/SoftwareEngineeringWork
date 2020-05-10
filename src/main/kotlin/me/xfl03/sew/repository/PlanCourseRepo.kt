package me.xfl03.sew.repository

import me.xfl03.framework.repo.Repo
import me.xfl03.framework.repo.Repository
import me.xfl03.sew.entity.PlanCourse

@Repo
interface PlanCourseRepo : Repository<PlanCourse> {
    fun listByPlanid(planId: Long): List<PlanCourse>
}