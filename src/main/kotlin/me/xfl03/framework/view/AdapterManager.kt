package me.xfl03.framework.view

import com.google.inject.Inject
import com.google.inject.Singleton
import me.xfl03.sew.repository.CourseRepo
import me.xfl03.sew.repository.PlanRepo
import me.xfl03.sew.repository.StudentRepo
import me.xfl03.sew.repository.TeacherRepo

@Singleton
class AdapterManager {
    @Inject
    lateinit var teacherRepo: TeacherRepo

    @Inject
    lateinit var courseRepo: CourseRepo

    @Inject
    lateinit var studentRepo: StudentRepo

    @Inject
    lateinit var planRepo: PlanRepo

    val adapters = HashMap<String, (Number) -> String>()

    val days = arrayOf("", "��һ", "�ܶ�", "����", "����", "����", "����", "����")
    val times = arrayOf(
        "", "08:00-09:45", "10:05-11:50", "13:30-15:15", "15:30-17:15", "18:30-20:15"
    )

    init {
        adapters["teacher"] = { teacherRepo.findById(it.toLong())?.name ?: "δ֪" }
        adapters["student"] = { studentRepo.findById(it.toLong())?.name ?: "δ֪" }
        adapters["course"] = { courseRepo.findById(it.toLong())?.name ?: "δ֪" }
        adapters["plan"] = { planRepo.findById(it.toLong())?.name ?: "δ֪" }

        adapters["time"] = { "${days[it.toInt() / 10]} ${times[it.toInt() % 10]}" }
        adapters["examTime"] = { times[it.toInt()] }
        adapters["score"] = { if (it == 0) "" else it.toString() }
    }

    fun getText(name: String, value: Number): String {
        return adapters[name]?.invoke(value) ?: value.toString()
    }
}