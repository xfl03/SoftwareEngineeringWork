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
        button("����") {
            action {
                ViewManager.back()
            }
        }
    }

    init {
        title = "ѡ��"
        text.promptText = "�γ���"

        //�γ�ɸѡ
        addChangeListener(text) { table.items = courseService.getCourses(it).asObservable() }

        //˫��ѡ��
        addClickListener(table, {}) {
            showConfirm("ȷ��ѡ��", it.name) {
                val result = courseService.selectCourse(student.id, it.id)
                if (result.first) {
                    showAlert("ѡ�γɹ�", result.second)
                } else {
                    showWarn("ѡ��ʧ��", result.second)
                }
            }
        }

        root.apply {
            add(text)
            add(table)
        }
    }
}