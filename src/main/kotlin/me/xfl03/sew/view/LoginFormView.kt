package me.xfl03.sew.view

import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Button
import me.xfl03.framework.util.TornadoFXUtil.showWarn
import me.xfl03.framework.view.ViewManager
import me.xfl03.sew.entity.Admin
import me.xfl03.sew.entity.Student
import me.xfl03.sew.repository.AdminRepo
import me.xfl03.sew.service.UserService
import me.xfl03.sew.view.student.StudentMenuView
import tornadofx.*


class LoginFormView : View() {
    private val userService: UserService by di()
    private lateinit var loginBtn: Button
    private val idProperty = SimpleStringProperty()
    private val pwProperty = SimpleStringProperty()
    override val root = form {
        setMinSize(233.0, 200.0)
        fieldset("��¼") {
            field("ID") {
                textfield(idProperty) {
                    filterInput { it.controlNewText.isInt() }
                }
            }
            field("����") {
                passwordfield(pwProperty) {
                }
            }
        }
        hbox {
            button("��¼") {
                loginBtn = this
                action {
                    if (idProperty.value == null || pwProperty.value == null) {
                        showWarn("��¼ʧ��", "����дID������")
                        return@action
                    }
                    val result = userService.login(idProperty.value.toLong(), pwProperty.value)
                    if (result) {
                        val user = userService.user
                        println(user)
                        when (user) {
                            is Admin -> ViewManager.display(TableView({ userService.adminRepo.list() }) {
                                var res = false
                                ViewManager.display(FormView(it) { o ->
                                    println(o)
                                    userService.adminRepo.save(o)
                                    res = true
                                })
                                res
                            })
                            is Student -> ViewManager.displayNew(StudentMenuView())
                        }
                    } else {
                        showWarn("��¼ʧ��", "ID���������")
                    }
                }
            }
            button("�˳�") {
                action { Platform.exit() }
            }
        }
    }

    init {
        title = "��¼"
        loginBtn.isDefaultButton = true

        ViewManager.add(this)
    }

}