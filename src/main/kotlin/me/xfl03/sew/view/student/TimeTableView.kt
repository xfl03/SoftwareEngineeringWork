package me.xfl03.sew.view.student

import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TableColumn
import me.xfl03.framework.view.AdapterManager
import me.xfl03.framework.view.Name
import me.xfl03.framework.view.ViewManager
import me.xfl03.sew.entity.Student
import me.xfl03.sew.service.CourseService
import me.xfl03.sew.service.UserService
import tornadofx.*

class TimeTableView : View() {
    val courseService: CourseService by di()
    val userService: UserService by di()
    val manager: AdapterManager by di()
    override val root = vbox {
        button("·µ»Ø") {
            action {
                ViewManager.back()
            }
        }
    }

    init {
        val arr = Array(5) { i ->
            Array(8) { j -> if (j == 0) manager.times[i + 1] else "" }
        }
        courseService.getSelectedCourse((userService.user as Student).id).forEach {
            arr[it.time % 10 - 1][it.time / 10] = it.name
        }
        val table = tableview<Array<String>> {
            items = arr.asList().asObservable()
            column("Ê±¼ä") { it: TableColumn.CellDataFeatures<Array<String>, String> ->
                SimpleStringProperty(it.value[0])
            }
            for (i in 1..7) {
                column(manager.days[i]) { it: TableColumn.CellDataFeatures<Array<String>, String> ->
                    SimpleStringProperty(it.value[i])
                }
            }
            //columnResizePolicy = SmartResize.POLICY
        }
        root.add(table)
    }
}