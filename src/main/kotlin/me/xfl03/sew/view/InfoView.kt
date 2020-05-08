package me.xfl03.sew.view

import me.xfl03.framework.util.TornadoFXUtil.createForm
import tornadofx.View

class InfoView<T : Any>(obj: T) : View() {
    override val root = createForm(obj, false)
}