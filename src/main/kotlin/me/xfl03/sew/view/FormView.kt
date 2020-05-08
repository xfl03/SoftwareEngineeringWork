package me.xfl03.sew.view

import me.xfl03.framework.util.TornadoFXUtil.createForm
import tornadofx.View

class FormView<T : Any>(obj: T, action: (T) -> Unit = {}) : View() {
    override val root = createForm(obj, true,  action)
}