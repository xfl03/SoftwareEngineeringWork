package me.xfl03.sew

import javafx.application.Application
import me.xfl03.framework.util.GuiceUtil
import me.xfl03.framework.util.HibernateUtil
import me.xfl03.framework.util.ReflectionsUtil
import me.xfl03.sew.service.Test
import me.xfl03.sew.view.Main

fun main() {
    HibernateUtil.init()
    ReflectionsUtil.registerRepos("me.xfl03.sew.repository")
    GuiceUtil.init()
    GuiceUtil.injector!!.getInstance(Test::class.java).test()

    Application.launch(Main::class.java, null)

    val t = {it:Boolean->it.toString()}
}