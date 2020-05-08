package me.xfl03.sew.service

import com.google.inject.Inject
import com.google.inject.Singleton
import me.xfl03.sew.entity.Loginable
import me.xfl03.sew.repository.AdminRepo
import me.xfl03.sew.repository.StudentRepo
import me.xfl03.sew.repository.TeacherRepo
import java.util.*

@Singleton
class UserService {
    @Inject
    lateinit var adminRepo: AdminRepo

    @Inject
    lateinit var studentRepo: StudentRepo

    @Inject
    lateinit var teacherRepo: TeacherRepo

    var user: Loginable? = null

    fun login(id: Long, pw: String): Boolean {
        //val i = id.toLong()
        user = adminRepo.findByIdAndPassword(id, pw)
        if (user == null) {
            user = teacherRepo.findByIdAndPassword(id, pw)
        }
        if (user == null) {
            user = studentRepo.findByIdAndPassword(id, pw)
        }
        return user != null
    }
}