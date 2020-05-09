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
                button("�鿴�α�") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(TimeTableView())
                    }
                }
                button("�鿴����") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                    }
                }
            }
            hbox {
                button("����ʱ��") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(TableView(courseService.examRepo, {
                            courseService.getExams(userService.getId())
                        }))
                    }
                }
                button("ѡ�ν��") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(TableView(courseService.courseSelectRepo, {
                            (it as CourseSelectRepo).listByStudentid(userService.getId())
                        }) {
                            var res = false
                            val course = courseService.getCourse(it.courseId)
                            if (course != null) {
                                showConfirm("ȷ���˿�", course.name) {
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
        }
    }

    init {
        title = "ѧ����"
    }
}