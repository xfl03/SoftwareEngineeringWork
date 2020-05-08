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
                button("基本信息") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(InfoView(student))
                    }
                }
                button("在线选课") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(CourseSelectView())
                    }
                }
            }
            hbox {
                button("网上评教") {
                    setMinSize(minWidth0, minHeight0)
                    action {

                    }
                }
                button("培养方案") {
                    setMinSize(minWidth0, minHeight0)
                    action {

                    }
                }
            }
            hbox {
                button("考试时间") {
                    setMinSize(minWidth0, minHeight0)
                    action {

                    }
                }
                button("查看绩点") {
                    setMinSize(minWidth0, minHeight0)
                    action {

                    }
                }
            }
            hbox {
                button("查看成绩") {
                    setMinSize(minWidth0, minHeight0)
                    action {

                    }
                }
                button("查看课表") {
                    setMinSize(minWidth0, minHeight0)
                    action {

                    }
                }
            }
        }
    }

    init {
        title = "学生端"
    }
}