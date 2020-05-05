package me.xfl03.sew.repository

import me.xfl03.framework.repo.Repo
import me.xfl03.framework.repo.Repository
import me.xfl03.sew.entity.Teacher

@Repo
interface TeacherRepo : Repository<Teacher> {}