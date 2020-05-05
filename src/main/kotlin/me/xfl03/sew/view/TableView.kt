package me.xfl03.sew.view

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TableColumn
import me.xfl03.framework.util.TornadoFXUtil
import me.xfl03.sew.entity.Admin
import tornadofx.View
import tornadofx.asObservable
import tornadofx.column
import tornadofx.tableview

class TableView : View() {
    private val admins = listOf(Admin(0, "a")).asObservable()
    @ExperimentalStdlibApi
    override val root = TornadoFXUtil.createTableView(admins, Admin::class)

}