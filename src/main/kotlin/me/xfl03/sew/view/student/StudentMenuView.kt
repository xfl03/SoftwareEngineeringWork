package me.xfl03.sew.view.student

import me.xfl03.framework.util.TornadoFXUtil.showAlert
import me.xfl03.framework.util.TornadoFXUtil.showConfirm
import me.xfl03.framework.util.TornadoFXUtil.showWarn
import me.xfl03.framework.view.ViewManager
import me.xfl03.sew.entity.Student
import me.xfl03.sew.service.CourseService
import me.xfl03.sew.service.UserService
import me.xfl03.sew.view.InfoView
import me.xfl03.sew.view.TableView
import me.xfl03.sew.view.TextAreaView
import tornadofx.*

class StudentMenuView : View() {
    val minHeight0 = 46.6
    val minWidth0 = 240.0

    val userService: UserService by di()
    val courseService: CourseService by di()

    val student = userService.user as Student
    override val root = form {
        setMinSize(485.0, 333.3)
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
                button("查看成绩") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(ExamResultView())
                    }
                }
            }
            hbox {
                button("考试时间") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(TableView({
                            courseService.getExams(userService.getId())
                        }))
                    }
                }
                button("选课结果") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(TableView( {
                            courseService.getSelectedCourse(userService.getId())
                        }) {
                            var res = false
                            val course = courseService.getCourse(it.id)
                            if (course != null) {
                                showConfirm("确认退课", course.name) {
                                    val ret = courseService.unselectCourse(userService.getId(), course.id)
                                    if (ret.first) {
                                        showAlert("退课成功", ret.second)
                                    } else {
                                        showWarn("退课失败", ret.second)
                                    }
                                    res = ret.first
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
                        ViewManager.display(TableView( {
                            courseService.getSelectedCourse(userService.getId())
                        }) { course ->
                            val has = courseService.hasEvaluation(userService.getId(), course.id)
                            if (has) {
                                showWarn("评教失败", "已经评教过")
                            } else {
                                ViewManager.display(TextAreaView() {
                                    courseService.addEvaluation(userService.getId(), course.id, it)
                                    showAlert("评教成功", "评教成功")
                                })
                            }
                            false
                        })
                    }
                }
                button("培养方案") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(PlanView())
                    }
                }
            }
        }
    }

    init {
        title = "学生端"
    }
}