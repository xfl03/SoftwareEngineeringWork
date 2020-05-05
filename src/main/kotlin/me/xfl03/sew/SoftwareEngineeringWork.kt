package me.xfl03.sew

import javafx.application.Application
import me.xfl03.framework.util.GuiceUtil
import me.xfl03.framework.util.HibernateUtil
import me.xfl03.framework.util.ReflectionsUtil
import me.xfl03.framework.util.TornadoFXUtil
import me.xfl03.sew.service.Test

fun main() {
    HibernateUtil.init()
    ReflectionsUtil.registerRepos("me.xfl03.sew.repository")
    GuiceUtil.init()
    TornadoFXUtil.initDI()

    //GuiceUtil.injector!!.getInstance(Test::class.java).test()

    Application.launch(FXApp::class.java, null)
}