package me.xfl03.sew.view.student

import me.xfl03.framework.util.TornadoFXUtil.addChangeListener
import me.xfl03.framework.util.TornadoFXUtil.addClickListener
import me.xfl03.framework.util.TornadoFXUtil.createTableView
import me.xfl03.framework.util.TornadoFXUtil.showAlert
import me.xfl03.framework.util.TornadoFXUtil.showConfirm
import me.xfl03.framework.util.TornadoFXUtil.showWarn
import me.xfl03.framework.view.AdapterManager
import me.xfl03.framework.view.ViewManager
import me.xfl03.sew.entity.Student
import me.xfl03.sew.service.CourseService
import me.xfl03.sew.service.UserService
import tornadofx.*

class CourseSelectView : View() {
    val adapterManager: AdapterManager by di()
    val userService: UserService by di()
    val student = userService.user as Student
    private val courseService: CourseService by di()

    val text = textfield("")
    val table = createTableView(courseService.getCourses(), adapterManager)

    override val root = vbox {
        button("返回") {
            action {
                ViewManager.back()
            }
        }
    }

    init {
        title = "选课"
        text.promptText = "课程名"

        //课程筛选
        addChangeListener(text) { table.items = courseService.getCourses(it).asObservable() }

        //双击选课
        addClickListener(table, {}) {
            showConfirm("确认选课", it.name) {
                val result = courseService.selectCourse(student.id, it.id)
                if (result.first) {
                    showAlert("选课成功", result.second)
                } else {
                    showWarn("选课失败", result.second)
                }
            }
        }

        root.apply {
            add(text)
            add(table)
        }
    }
}