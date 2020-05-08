package me.xfl03.sew.service

import com.google.inject.Inject
import com.google.inject.Singleton
import me.xfl03.sew.entity.Course
import me.xfl03.sew.entity.CourseSelect
import me.xfl03.sew.repository.CourseRepo
import me.xfl03.sew.repository.CourseSelectRepo

@Singleton
class CourseService {
    @Inject
    lateinit var courseRepo: CourseRepo

    @Inject
    lateinit var courseSelectRepo: CourseSelectRepo

    fun getCourses(name: String? = null): List<Course> {
        val list =
            if (name.isNullOrBlank()) courseRepo.list()
            else courseRepo.listByNameContains(name.trim())
        return list
    }

    fun selectCourse(studentId:Long,courseId: Long) {
        courseSelectRepo.save(CourseSelect(0, studentId, courseId))
    }
}