package me.xfl03.sew.service

import com.google.inject.Inject
import com.google.inject.Singleton
import me.xfl03.sew.entity.*
import me.xfl03.sew.repository.*

@Singleton
class CourseService {
    @Inject
    lateinit var courseRepo: CourseRepo

    @Inject
    lateinit var courseSelectRepo: CourseSelectRepo

    @Inject
    lateinit var examRepo: ExamRepo

    @Inject
    lateinit var evaluationRepo: EvaluationRepo

    @Inject
    lateinit var planRepo: PlanRepo

    @Inject
    lateinit var planCourseRepo: PlanCourseRepo

    fun getCourses(name: String? = null): List<Course> {
        val list =
            if (name.isNullOrBlank()) courseRepo.list()
            else courseRepo.listByNameContains(name.trim())
        return list
    }

    fun getSelectedCourse0(studentId: Long): List<CourseSelect> {
        return courseSelectRepo.listByStudentid(studentId)
    }

    fun getSelectedCourse(studentId: Long, filter: (CourseSelect) -> Boolean = { true }): List<Course> {
        val selected = courseSelectRepo.listByStudentid(studentId).filter(filter)
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

    fun unselectCourse(studentId: Long, courseId: Long): Pair<Boolean, String> {
        val cs = courseSelectRepo.findByStudentidAndCourseid(studentId, courseId)
            ?: return Pair(false, "课程不存在")
        if (cs.score > 0) {
            return Pair(false, "已出成绩")
        }
        courseSelectRepo.delete(cs)
        return Pair(true, "退课成功")
    }

    fun getExams(studentId: Long): List<Exam> {
        val selected = courseSelectRepo.listByStudentid(studentId)
        return examRepo.listByCourseidIn(selected.map { it.courseId })
    }

    fun getGPA(studentId: Long): Double {
        val selected = courseSelectRepo.listByStudentid(studentId).filter { it.score > 0 }
        if (selected.isEmpty()) {
            return -0.0
        }
        val courses = courseRepo.listByIdIn(selected.map { it.courseId }).map { it.id to it.credit }.toMap()
        if (courses.isEmpty()) {
            return -0.0
        }
        var sum = 0.0
        var credits = 0
        selected.forEach {
            if (courses[it.courseId] != null) {
                val credit = (courses[it.courseId] ?: 0)
                val gpa = getGPA(it.score)
                sum += credit * gpa
                credits += credit
            }
        }
        return sum / credits
    }

    fun getGPA(score: Int): Double {
        return when (score) {
            in 90..100 -> 4.0
            in 85..89 -> 3.7
            in 82..84 -> 3.3
            in 78..81 -> 3.0
            in 75..77 -> 2.7
            in 72..74 -> 2.3
            in 68..71 -> 2.0
            in 64..67 -> 1.7
            in 60..63 -> 1.0
            else -> 0.0
        }
    }

    fun hasEvaluation(studentId: Long, courseId: Long): Boolean {
        return evaluationRepo.findByStudentidAndCourseid(studentId, courseId) != null
    }

    fun addEvaluation(studentId: Long, courseId: Long, comment: String) {
        evaluationRepo.save(Evaluation(0, courseId, studentId, comment))
    }

    fun getPlanCourses(planId: Long): List<Course> {
        val cs = planCourseRepo.listByPlanid(planId)
        return courseRepo.listByIdIn(cs.map { it.courseId })
    }

    fun getPlan(planId: Long): Plan? {
        return planRepo.findById(planId)
    }
}