package me.xfl03.sew.view

import me.xfl03.framework.repo.Repository
import me.xfl03.framework.util.TornadoFXUtil.addDoubleClickListener
import me.xfl03.framework.util.TornadoFXUtil.createTableView
import me.xfl03.framework.view.ViewManager

import tornadofx.View
import kotlin.reflect.KCallable

class TableView<T : Any>(repo: Repository<T>, listFunc: KCallable<List<T>>) : View() {
    override var root = createTableView(listFunc.call(repo))

    init {
        addDoubleClickListener(root) {
            ViewManager.display(FormView(it) { o ->
                println(o)
                repo.save(o)
                root.items.clear()
                root.items.addAll(listFunc.call(repo))
            })
        }
    }
}