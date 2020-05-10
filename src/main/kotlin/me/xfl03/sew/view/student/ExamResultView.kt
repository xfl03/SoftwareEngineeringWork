package me.xfl03.sew.view.student

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TableColumn
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.text.Text
import me.xfl03.framework.view.ViewManager
import me.xfl03.sew.entity.CourseSelect
import me.xfl03.sew.service.CourseService
import me.xfl03.sew.service.UserService
import tornadofx.*

class ExamResultView : View() {
    val courseService: CourseService by di()
    val userService: UserService by di()

    lateinit var gpaField: Text
    override val root = vbox {
        hbox {
            button("返回") {
                action {
                    ViewManager.back()
                }
            }
            pane {
                HBox.setHgrow(this, Priority.ALWAYS)
                setMinSize(10.0, 1.0)
            }
            text {
                gpaField = this
            }
        }
    }

    init {
        val gpa = courseService.getGPA(userService.getId())
        if (gpa == -0.0) {
            gpaField.text = "暂无绩点"
        } else {
            gpaField.text = String.format("绩点：%.1f", gpa)
        }

        val selected = courseService.getSelectedCourse0(userService.getId()).filter { it.score != 0 }
        val map = courseService.getSelectedCourse(userService.getId()).map { it.id to Pair(it.name, it.credit) }.toMap()

        val table = tableview<CourseSelect> {
            items = selected.asObservable()
            column("课程") { it: TableColumn.CellDataFeatures<CourseSelect, String> ->
                SimpleStringProperty(map[it.value.courseId]?.first)
            }
            column("学分") { it: TableColumn.CellDataFeatures<CourseSelect, Number> ->
                SimpleIntegerProperty(map[it.value.courseId]?.second ?: 0)
            }
            column("成绩") { it: TableColumn.CellDataFeatures<CourseSelect, Number> ->
                SimpleIntegerProperty(it.value.score)
            }
            column("绩点") { it: TableColumn.CellDataFeatures<CourseSelect, Number> ->
                SimpleDoubleProperty(courseService.getGPA(it.value.score))
            }
            //columnResizePolicy = SmartResize.POLICY
        }
        root.add(table)
    }
}