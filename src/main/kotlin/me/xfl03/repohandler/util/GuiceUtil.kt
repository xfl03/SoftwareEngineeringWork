package me.xfl03.repohandler.util

import com.google.inject.Guice
import com.google.inject.Injector
import me.xfl03.repohandler.Repository
import java.util.*

internal object GuiceUtil {
    var guiceModule: GuiceModule = GuiceModule()
    var injector: Injector? = null
    var clazzes: MutableList<Class<out Repository<*>?>> =
        LinkedList()

    fun init() {
        injector = Guice.createInjector(guiceModule)
    }

    fun registerRepo(clazz: Class<*>) {
        guiceModule.add(clazz, ReflectionUtil.getProxyInstance(clazz))
    }
}