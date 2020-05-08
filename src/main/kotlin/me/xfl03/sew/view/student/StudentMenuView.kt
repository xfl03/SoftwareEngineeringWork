package me.xfl03.sew.view.student

import me.xfl03.framework.view.ViewManager
import me.xfl03.sew.entity.Student
import me.xfl03.sew.service.UserService
import me.xfl03.sew.view.InfoView
import tornadofx.*

class StudentMenuView : View() {
    val minHeight0 = 46.6
    val minWidth0 = 166.6
    val userService: UserService by di()
    val student = userService.user as Student
    override val root = form {
        setMinSize(333.3, 333.3)
        fieldset(student.name) {
            hbox {
                button("������Ϣ") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(InfoView(student))
                    }
                }
                button("����ѡ��") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(CourseSelectView())
                    }
                }
            }
            hbox {
                button("��������") {
                    setMinSize(minWidth0, minHeight0)
                    action {

                    }
                }
                button("��������") {
                    setMinSize(minWidth0, minHeight0)
                    action {

                    }
                }
            }
            hbox {
                button("����ʱ��") {
                    setMinSize(minWidth0, minHeight0)
                    action {

                    }
                }
                button("�鿴����") {
                    setMinSize(minWidth0, minHeight0)
                    action {

                    }
                }
            }
            hbox {
                button("�鿴�ɼ�") {
                    setMinSize(minWidth0, minHeight0)
                    action {

                    }
                }
                button("�鿴�α�") {
                    setMinSize(minWidth0, minHeight0)
                    action {

                    }
                }
            }
        }
    }

    init {
        title = "ѧ����"
    }
}