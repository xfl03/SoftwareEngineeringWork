package me.xfl03.sew.view

import me.xfl03.framework.util.TornadoFXUtil.createInfo
import me.xfl03.framework.view.AdapterManager
import tornadofx.View

class InfoView<T : Any>(obj: T) : View() {
    val manager: AdapterManager by di()
    override val root = createInfo(obj, manager)
}