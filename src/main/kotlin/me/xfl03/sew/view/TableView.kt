package me.xfl03.sew.view

import javafx.scene.Node
import javafx.scene.Parent
import me.xfl03.framework.util.TornadoFXUtil.addDoubleClickListener
import me.xfl03.framework.util.TornadoFXUtil.createForm
import me.xfl03.framework.util.TornadoFXUtil.createTableView
import me.xfl03.sew.repository.AdminRepo
import me.xfl03.sew.repository.CourseRepo

import tornadofx.View

class TableView : View() {
    val adminRepo: AdminRepo by di()
    val courseRepo: CourseRepo by di()
    val table = createTableView(adminRepo.list())
    override var root: Parent = table

    init {
        addDoubleClickListener(table) {
            replaceWith(FormView(it,this))
        }
    }
}