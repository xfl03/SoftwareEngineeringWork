package me.xfl03.sew.service

import com.google.inject.Inject
import me.xfl03.sew.entity.Admin
import me.xfl03.sew.repository.UserRepo

class Test {
    @Inject
    lateinit var repo: UserRepo

    fun test() {
        //repo.insert(Admin(1, "c"))
        //println(repo.selectByIdAndPassword(1, "c"))
    }
}