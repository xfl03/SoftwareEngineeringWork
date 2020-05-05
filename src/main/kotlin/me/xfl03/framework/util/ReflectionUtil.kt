package me.xfl03.framework.util

import me.xfl03.framework.repo.RepoHandler
import java.lang.reflect.InvocationHandler
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Proxy
import java.lang.reflect.Type

object ReflectionUtil {
    private const val TYPE_CLASS_NAME_PREFIX = "class "
    private const val TYPE_INTERFACE_NAME_PREFIX = "interface "

    fun getClassName(type: Type?): String {
        if (type == null) {
            return ""
        }
        var className = type.toString()
        if (className.startsWith(TYPE_CLASS_NAME_PREFIX)) {
            className = className.substring(TYPE_CLASS_NAME_PREFIX.length)
        } else if (className.startsWith(TYPE_INTERFACE_NAME_PREFIX)) {
            className = className.substring(TYPE_INTERFACE_NAME_PREFIX.length)
        }
        return className
    }

    fun getClass(type: Type?): Class<*>? {
        val className = getClassName(type)
        return if (className.isEmpty()) {
            null
        } else Class.forName(className)
    }

    fun getParameterizedTypes(clazz: Class<*>): Array<Type>? {
        val superclassType = clazz.genericInterfaces
        return if (superclassType.isNullOrEmpty() || superclassType[0] !is ParameterizedType) {
            null
        } else (superclassType[0] as ParameterizedType).actualTypeArguments
    }

    fun <T> getProxyInstance(clazz: Class<T>): T {
        val proxyClazz = Proxy.getProxyClass(clazz.classLoader, clazz)
        val constructor = proxyClazz.getConstructor(InvocationHandler::class.java)
        val instance = constructor.newInstance(RepoHandler(clazz))
        return clazz.cast(instance)
    }
}