package me.xfl03.sew.view

import me.xfl03.framework.repo.Repository
import me.xfl03.framework.util.TornadoFXUtil.addDoubleClickListener
import me.xfl03.framework.util.TornadoFXUtil.createTableView
import me.xfl03.framework.view.AdapterManager
import me.xfl03.framework.view.ViewManager

import tornadofx.View
import kotlin.reflect.KCallable

class TableView<T : Any>(
    repo: Repository<T>,
    listFunc: (Repository<T>) -> List<T>,
    action: (T) -> Boolean = { false }
) : View() {
    val adapterManager: AdapterManager by di()
    override var root = createTableView(listFunc.invoke(repo), adapterManager)

    init {
        addDoubleClickListener(root) {
            val result = action.invoke(it)
            if (result) {
                root.items.clear()
                root.items.addAll(listFunc.invoke(repo))
            }
        }
    }
}