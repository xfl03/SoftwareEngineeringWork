package me.xfl03.sew.view.teacher

import me.xfl03.framework.view.ViewManager
import me.xfl03.sew.entity.Teacher
import me.xfl03.sew.service.CourseService
import me.xfl03.sew.service.UserService
import me.xfl03.sew.view.InfoView
import me.xfl03.sew.view.TableView
import me.xfl03.sew.view.TextFieldView
import me.xfl03.sew.view.student.CourseSelectView
import tornadofx.*

class TeacherMenuView : View() {
    val minHeight0 = 46.6
    val minWidth0 = 240.0

    val userService: UserService by di()
    val courseService: CourseService by di()

    val teacher = userService.user as Teacher

    override val root = form {
        setMinSize(485.0, 333.3)
        fieldset(teacher.name) {
            hbox {
                button("基本信息") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(InfoView(teacher))
                    }
                }
            }
            hbox {
                button("选课名单") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(TableView({
                            courseService.courseRepo.listByTeacherid(teacher.id)
                        }) {
                            ViewManager.display(TableView({
                                courseService.courseSelectRepo.listByCourseid(it.id)
                            }))
                            false
                        })
                    }
                }
                button("成绩录入") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(TableView({
                            courseService.courseRepo.listByTeacherid(teacher.id)
                        }) {
                            ViewManager.display(TableView({
                                courseService.courseSelectRepo.listByCourseid(it.id)
                            }) { cs ->
                                ViewManager.display(TextFieldView() {
                                    cs.score = it.toInt()
                                    courseService.courseSelectRepo.save(cs)
                                })
                                true
                            })
                            false
                        })
                    }
                }
            }
        }
    }
}