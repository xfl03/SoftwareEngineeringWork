package me.xfl03.sew.view

import javafx.scene.layout.VBox
import me.xfl03.framework.util.DebounceExecutor
import me.xfl03.framework.util.TornadoFXUtil.addChangeListener
import me.xfl03.framework.util.TornadoFXUtil.addDoubleClickListener
import me.xfl03.framework.util.TornadoFXUtil.createTableView
import me.xfl03.framework.util.TornadoFXUtil.showAlert
import me.xfl03.framework.util.TornadoFXUtil.showConfirm
import me.xfl03.sew.service.CourseService
import tornadofx.View
import tornadofx.asObservable
import tornadofx.textfield

class CourseSelectView : View() {
    private val courseService: CourseService by di()
    override val root = VBox()

    init {
        title = "选课"
        val text = textfield("")
        val table = createTableView(courseService.getCourses())

        //课程筛选
        addChangeListener(text) { table.items = courseService.getCourses(it).asObservable() }

        //双击选课
        addDoubleClickListener(table) {
            showConfirm("确认选课", it.name) {
                courseService.selectCourse(it.id)
                showAlert("选课成功", "选课成功")
            }
        }

        root.apply {
            add(text)
            add(table)
        }
    }
}