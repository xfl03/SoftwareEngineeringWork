package me.xfl03.sew.view

import me.xfl03.framework.repo.Repository
import me.xfl03.framework.util.TornadoFXUtil.addDoubleClickListener
import me.xfl03.framework.util.TornadoFXUtil.createTableView
import me.xfl03.framework.view.AdapterManager
import me.xfl03.framework.view.ViewManager

import tornadofx.*

class TableView<T : Any>(
    listFunc: () -> List<T>,
    action: (T) -> Boolean = { false }
) : View() {
    val adapterManager: AdapterManager by di()
    val table = createTableView(listFunc.invoke(), adapterManager)
    override var root = vbox {
        button("·µ»Ø") {
            action{
                ViewManager.back()
            }
        }
    }

    init {
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