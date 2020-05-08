package me.xfl03.sew

import javafx.application.Platform
import me.xfl03.sew.view.*
import tornadofx.App


class FXApp : App(LoginFormView::class){
    override fun stop() {
        super.stop()
        Platform.exit()
    }
}