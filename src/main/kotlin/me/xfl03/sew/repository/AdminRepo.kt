package me.xfl03.sew.repository

import me.xfl03.framework.repo.Repo
import me.xfl03.framework.repo.Repository
import me.xfl03.sew.entity.Admin

@Repo
interface AdminRepo : Repository<Admin> {
    fun findByIdAndPassword(id: Long, password: String): Admin?
}