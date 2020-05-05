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
        title = "ѡ��"
        val text = textfield("")
        val table = createTableView(courseService.getCourses())

        //�γ�ɸѡ
        addChangeListener(text) { table.items = courseService.getCourses(it).asObservable() }

        //˫��ѡ��
        addDoubleClickListener(table) {
            showConfirm("ȷ��ѡ��", it.name) {
                courseService.selectCourse(it.id)
                showAlert("ѡ�γɹ�", "ѡ�γɹ�")
            }
        }

        root.apply {
            add(text)
            add(table)
        }
    }
}