package me.xfl03.framework.util

import me.xfl03.framework.repo.Repo
import org.reflections.Reflections

object ReflectionsUtil {
    fun registerRepos(packageName:String){
        val reflections = Reflections(packageName)
        val classesList = reflections.getTypesAnnotatedWith(Repo::class.java)
        for (clazz in classesList) {
            GuiceUtil.registerRepo(clazz)
        }
    }
}