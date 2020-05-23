package me.xfl03.sew.view.admin


import me.xfl03.framework.repo.Repository
import me.xfl03.framework.view.ViewManager
import me.xfl03.sew.entity.*
import me.xfl03.sew.service.CourseService
import me.xfl03.sew.service.UserService
import me.xfl03.sew.view.FormView
import me.xfl03.sew.view.TableView
import tornadofx.*

class AdminMenuView : View() {
    val minHeight0 = 46.6
    val minWidth0 = 240.0

    val userService: UserService by di()
    val courseService: CourseService by di()

    val admin = userService.user as Admin

    override val root = form {
        setMinSize(485.0, 333.3)
        fieldset(admin.name) {
            hbox {
                button("学生管理") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        basicOnAction("学生管理", userService.studentRepo, Student::class.java)
                    }
                }
                button("教师管理") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        basicOnAction("教师管理", userService.teacherRepo, Teacher::class.java)
                    }
                }
            }
            hbox {
                button("课程管理") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        basicOnAction("课程管理", courseService.courseRepo, Course::class.java)
                    }
                }
                button("选课管理") {
                    setMinSize(minWidth0, minHeight0)
                    action {
                        basicOnAction("选课管理", courseService.courseSelectRepo, CourseSelect::class.java)
                    }
                }
            }
        }
    }

    init {
        title = "管理员端"
    }

    private fun <T : Any> basicOnAction(title: String, repo: Repository<T>, clazz: Class<T>) {
        ViewManager.display(
            TableView<T>(
                { repo.list() },
                title,
                mapOf(
                    "删除" to { it: T? ->
                        if (it != null) {
                            repo.delete(it)
                        }
                    },
                    "新增" to { it: T? ->
                        ViewManager.display(FormView(clazz.newInstance()) {
                            repo.save(it)
                        })
                    }
                )
            ) { it: T ->
                ViewManager.display(FormView(it) { o ->
                    println(o)
                    repo.save(o)
                })
            }
        )
    }
}