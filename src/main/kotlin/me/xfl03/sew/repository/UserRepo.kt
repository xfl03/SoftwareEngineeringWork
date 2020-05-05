package me.xfl03.sew.repository

import me.xfl03.framework.repo.Repo
import me.xfl03.framework.repo.Repository
import me.xfl03.sew.entity.Admin

@Repo
interface UserRepo : Repository<Admin> {
    fun selectByIdAndPassword(id: Long, password: String): Admin?
}