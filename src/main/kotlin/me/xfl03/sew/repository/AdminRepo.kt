package me.xfl03.sew.repository

import me.xfl03.repohandler.Repo
import me.xfl03.repohandler.Repository
import me.xfl03.sew.entity.Admin

@Repo
interface AdminRepo : Repository<Admin> {
    fun selectByIdAndPassword(id: Long, password: String): Admin?
}