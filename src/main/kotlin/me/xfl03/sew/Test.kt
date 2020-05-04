package me.xfl03.sew

import com.google.inject.Inject
import me.xfl03.sew.entity.Admin
import me.xfl03.sew.repository.AdminRepo

class Test {
    @Inject
    lateinit var repo: AdminRepo
    fun test() {
        repo.insert(Admin(1, "c"))
        println(repo.selectByIdAndPassword(1, "c"))
    }
}