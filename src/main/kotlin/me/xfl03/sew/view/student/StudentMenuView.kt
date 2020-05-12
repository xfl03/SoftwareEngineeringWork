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
                button("�鿴�ɼ�") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(ExamResultView())
                    }
                }
            }
            hbox {
                button("����ʱ��") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(TableView({
                            courseService.getExams(userService.getId())
                        }))
                    }
                }
                button("ѡ�ν��") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(TableView( {
                            courseService.getSelectedCourse(userService.getId())
                        }) {
                            var res = false
                            val course = courseService.getCourse(it.id)
                            if (course != null) {
                                showConfirm("ȷ���˿�", course.name) {
                                    val ret = courseService.unselectCourse(userService.getId(), course.id)
                                    if (ret.first) {
                                        showAlert("�˿γɹ�", ret.second)
                                    } else {
                                        showWarn("�˿�ʧ��", ret.second)
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
                button("��������") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(TableView( {
                            courseService.getSelectedCourse(userService.getId())
                        }) { course ->
                            val has = courseService.hasEvaluation(userService.getId(), course.id)
                            if (has) {
                                showWarn("����ʧ��", "�Ѿ����̹�")
                            } else {
                                ViewManager.display(TextAreaView() {
                                    courseService.addEvaluation(userService.getId(), course.id, it)
                                    showAlert("���̳ɹ�", "���̳ɹ�")
                                })
                            }
                            false
                        })
                    }
                }
                button("��������") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        ViewManager.display(PlanView())
                    }
                }
            }
        }
    }

    init {
        title = "ѧ����"
    }
}