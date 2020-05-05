package me.xfl03.sew.view

import me.xfl03.framework.util.TornadoFXUtil.createForm
import me.xfl03.sew.entity.Admin
import tornadofx.View

class FormView(admin: Admin, pre: View? = null) : View() {
    override val root = createForm(admin, this, pre)
}