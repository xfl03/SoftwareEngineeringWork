package me.xfl03.sew.service

import com.google.inject.Inject
import com.google.inject.Singleton
import me.xfl03.sew.entity.Course
import me.xfl03.sew.entity.CourseSelect
import me.xfl03.sew.entity.Exam
import me.xfl03.sew.repository.CourseRepo
import me.xfl03.sew.repository.CourseSelectRepo
import me.xfl03.sew.repository.ExamRepo

@Singleton
class CourseService {
    @Inject
    lateinit var courseRepo: CourseRepo

    @Inject
    lateinit var courseSelectRepo: CourseSelectRepo

    @Inject
    lateinit var examRepo: ExamRepo

    fun getCourses(name: String? = null): List<Course> {
        val list =
            if (name.isNullOrBlank()) courseRepo.list()
            else courseRepo.listByNameContains(name.trim())
        return list
    }

    fun getSelectedCourse(studentId: Long): List<Course> {
        val selected = courseSelectRepo.listByStudentid(studentId)
        return courseRepo.listByIdIn(selected.map { it.courseId })
    }

    fun selectCourse(studentId: Long, courseId: Long): Pair<Boolean, String> {
        val selected = courseSelectRepo.listByStudentid(studentId)
        if (selected.map { it.courseId }.contains(courseId)) {
            return Pair(false, "已选该课")
        }
        val courses = courseRepo.listByIdIn(selected.map { it.courseId })
        val course = courseRepo.findById(courseId)!!
        if (courses.map { it.time }.contains(course.time)) {
            return Pair(false, "时间冲突")
        }
        courseSelectRepo.save(CourseSelect(0, studentId, courseId))
        return Pair(true, "选课成功")
    }

    fun getCourse(courseId: Long): Course? {
        return courseRepo.findById(courseId)
    }

    fun unselectCourse(cs: CourseSelect) {
        courseSelectRepo.delete(cs)
    }

    fun getExams(studentId: Long): List<Exam> {
        val selected = courseSelectRepo.listByStudentid(studentId)
        return examRepo.listByCourseidIn(selected.map { it.courseId })
    }
}