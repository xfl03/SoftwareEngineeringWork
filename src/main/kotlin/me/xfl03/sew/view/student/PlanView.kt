package me.xfl03.sew.view.student

import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.text.Text
import me.xfl03.framework.view.BooleanAdapter
import me.xfl03.framework.view.Name
import me.xfl03.framework.view.ViewManager
import me.xfl03.framework.util.TornadoFXUtil.createTableView
import me.xfl03.framework.view.Order
import me.xfl03.sew.entity.Student
import me.xfl03.sew.service.CourseService
import me.xfl03.sew.service.UserService
import tornadofx.*

class PlanView : View() {
    val courseService: CourseService by di()
    val userService: UserService by di()

    lateinit var creditField: Text
    override val root = vbox {
        hbox {
            button("返回") {
                action {
                    ViewManager.back()
                }
            }
            pane {
                HBox.setHgrow(this, Priority.ALWAYS)
                setMinSize(10.0, 1.0)
            }
            text {
                creditField = this
            }
        }
    }

    init {
        val student = userService.user as Student
        val plan = courseService.getPlan(student.planId)
        if (plan != null) {
            title = plan.name
            val sc = courseService.getSelectedCourse(student.id) { it.score >= 60 }.map { it.id }
            var credits = 0
            val pc = courseService.getPlanCourses(student.planId).map {
                if (sc.contains(it.id)) {
                    credits += it.credit
                }
                Entity(it.name, it.credit, sc.contains(it.id))
            }

            creditField.text = "计划完成情况：已修读学分${credits} / 总学分${plan.credits}"

            val table = createTableView(pc)
            root.add(table)
        }
    }

    data class Entity(
        @Name("课程名")
        @Order(1)
        var name: String,
        @Name("学分")
        @Order(2)
        var credit: Int,
        @Name("已修读")
        @Order(3)
        @BooleanAdapter(t = "是", f = "")
        var finished: Boolean
    )
}