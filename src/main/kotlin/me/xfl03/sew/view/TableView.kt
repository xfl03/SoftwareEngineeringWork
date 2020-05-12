package me.xfl03.sew.view

import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import me.xfl03.framework.repo.Repository
import me.xfl03.framework.util.TornadoFXUtil.addDoubleClickListener
import me.xfl03.framework.util.TornadoFXUtil.addSingleClickListener
import me.xfl03.framework.util.TornadoFXUtil.createTableView
import me.xfl03.framework.view.AdapterManager
import me.xfl03.framework.view.ViewManager

import tornadofx.*

class TableView<T : Any>(
    listFunc: () -> List<T>,
    buttons: Map<String, (T?) -> Boolean> = emptyMap(),
    action: (T) -> Boolean = { false }
) : View() {
    val adapterManager: AdapterManager by di()
    val table = createTableView(listFunc.invoke(), adapterManager)
    var item: T? = null
    lateinit var hbox: HBox
    override var root = vbox {
        hbox {
            hbox = this
            button("·µ»Ø") {
                action {
                    ViewManager.back()
                }
            }
            pane {
                HBox.setHgrow(this, Priority.ALWAYS)
                setMinSize(10.0, 1.0)
            }
        }
    }

    init {
        buttons.forEach {
            val btn = button(it.key) {
                action {
                    it.value.invoke(item)
                }
            }
            hbox.add(btn)
        }
        addSingleClickListener(table) {
            item = it
        }
        addDoubleClickListener(table) {
            val result = action.invoke(it)
            if (result) {
                table.items.clear()
                table.items.addAll(listFunc.invoke())
            }
        }
        table.columnResizePolicy = SmartResize.POLICY
        root.add(table)
    }
}