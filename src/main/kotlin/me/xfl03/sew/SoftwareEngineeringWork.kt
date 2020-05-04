package me.xfl03.sew

import me.xfl03.repohandler.util.GuiceUtil
import me.xfl03.repohandler.util.HibernateUtil
import me.xfl03.repohandler.util.ReflectionsUtil

fun main() {
    HibernateUtil.init()
    ReflectionsUtil.registerRepos("me.xfl03.sew.repository")
    GuiceUtil.init()
    GuiceUtil.injector!!.getInstance(Test::class.java).test()
}