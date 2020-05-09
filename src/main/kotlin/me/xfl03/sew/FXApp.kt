package me.xfl03.sew

import javafx.stage.Stage
import me.xfl03.framework.view.ViewManager
import me.xfl03.sew.view.*
import tornadofx.App


class FXApp : App(LoginFormView::class) {
    override fun start(stage: Stage) {
        stage.setOnHiding {
            ViewManager.tryExit()
        }
        super.start(stage)
    }
}