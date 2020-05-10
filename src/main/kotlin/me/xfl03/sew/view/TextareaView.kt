package me.xfl03.sew.view

import javafx.scene.control.TextArea
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import me.xfl03.framework.view.ViewManager
import tornadofx.*

class TextareaView(action: (String) -> Unit) : View() {
    private lateinit var textArea: TextArea
    override val root = vbox {
        textarea {
            textArea = this
        }
        hbox {
            button("Ã·Ωª") {
                action {
                    action.invoke(textArea.text)
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