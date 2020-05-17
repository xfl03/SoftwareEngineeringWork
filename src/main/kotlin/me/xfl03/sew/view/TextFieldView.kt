package me.xfl03.sew.view

import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import me.xfl03.framework.view.ViewManager
import tornadofx.*

class TextFieldView(action: (String) -> Unit) : View() {
    private lateinit var textField: TextField
    override val root = vbox {
        textfield {
            textField = this
        }
        hbox {
            button("Ã·Ωª") {
                action {
                    action.invoke(textField.text)
                    ViewManager.back()
                }
            }
            pane {
                HBox.setHgrow(this, Priority.ALWAYS)
                setMinSize(10.0, 1.0)
            }
            button("∑µªÿ") {
                action {
                    ViewManager.back()
                }
            }
        }
    }
}