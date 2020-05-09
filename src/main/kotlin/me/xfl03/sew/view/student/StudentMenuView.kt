package me.xfl03.sew.view.student

import me.xfl03.framework.util.TornadoFXUtil.showConfirm
import me.xfl03.framework.view.ViewManager
import me.xfl03.sew.entity.CourseSelect
import me.xfl03.sew.entity.Student
import me.xfl03.sew.repository.CourseSelectRepo
import me.xfl03.sew.repository.ExamRepo
import me.xfl03.sew.service.CourseService
import me.xfl03.sew.service.UserService
import me.xfl03.sew.view.InfoView
import me.xfl03.sew.view.TableView
import tornadofx.*

class StudentMenuView : View() {
    val minHeight0 = 46.6
    val minWidth0 = 230.0

    val userService: UserService by di()
    val courseService: CourseService by di()

    val student = userService.user as Student
    override val root = form {
        setMinSize(466.6, 333.3)
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
                button("查看课表") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(TimeTableView())
                    }
                }
                button("查看绩点") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                    }
                }
            }
            hbox {
                button("考试时间") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(TableView(courseService.examRepo, {
                            courseService.getExams(userService.getId())
                        }))
                    }
                }
                button("选课结果") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(TableView(courseService.courseSelectRepo, {
                            (it as CourseSelectRepo).listByStudentid(userService.getId())
                        }) {
                            var res = false
                            val course = courseService.getCourse(it.courseId)
                            if (course != null) {
                                showConfirm("确认退课", course.name) {
                                    courseService.unselectCourse(it)
                                    res = true
                                }
                            }
                            res
                        })
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
        }
    }

    init {
        title = "学生端"
    }
}