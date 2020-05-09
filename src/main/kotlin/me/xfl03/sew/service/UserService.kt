package me.xfl03.sew.service

import com.google.inject.Inject
import com.google.inject.Singleton
import me.xfl03.sew.entity.Admin
import me.xfl03.sew.entity.Loginable
import me.xfl03.sew.entity.Student
import me.xfl03.sew.entity.Teacher
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

    fun getId(): Long {
        if (user is Admin) {
            return (user as Admin).id
        }
        if (user is Teacher) {
            return (user as Teacher).id
        }
        if (user is Student) {
            return (user as Student).id
        }
        return -1
    }
}