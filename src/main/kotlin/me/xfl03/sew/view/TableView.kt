package me.xfl03.sew.view

import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import me.xfl03.framework.util.TornadoFXUtil.addClickListener
import me.xfl03.framework.util.TornadoFXUtil.createTableView
import me.xfl03.framework.view.AdapterManager
import me.xfl03.framework.view.BackEventListener
import me.xfl03.framework.view.ViewManager

import tornadofx.*

class TableView<T : Any>(
    val listFunc: () -> List<T>,
    val title0: String = "",
    buttons: Map<String, (T?) -> Unit> = emptyMap(),
    action: (T) -> Unit = { }
) : View(), BackEventListener {
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
        title = title0
        buttons.forEach {
            val btn = button(it.key) {
                action {
                    it.value.invoke(item)
                    onBack()
                }
            }
            hbox.add(btn)
        }
        addClickListener(table, {
            item = it
        }, {
            action.invoke(it)
        })
        table.columnResizePolicy = SmartResize.POLICY
        root.add(table)
    }

    override fun onBack() {
        table.items.clear()
        table.items.addAll(listFunc.invoke())
    }
}