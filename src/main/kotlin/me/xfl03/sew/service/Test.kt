package me.xfl03.sew.service

import com.google.inject.Inject
import com.google.inject.Singleton
import me.xfl03.sew.entity.Course
import me.xfl03.sew.repository.AdminRepo
import me.xfl03.sew.repository.CourseRepo

@Singleton
class Test {
    @Inject
    lateinit var adminRepo: AdminRepo
    @Inject
    lateinit var courseRepo: CourseRepo

    fun test() {
        courseRepo.save(Course(0,"ABC",0,10))
        courseRepo.save(Course(1,"aABC",0,10))
        courseRepo.save(Course(2,"ABCb",0,10))
        courseRepo.save(Course(3,"aABCb",0,10))
    }
}