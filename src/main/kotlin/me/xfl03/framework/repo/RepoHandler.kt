package me.xfl03.framework.repo

import me.xfl03.framework.util.HibernateUtil
import me.xfl03.framework.util.ReflectionUtil
import org.hibernate.Session
import org.hibernate.query.Query
import java.lang.StringBuilder
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.util.*

class RepoHandler(clazz: Class<*>) : InvocationHandler {
    val entityClass: Class<*>

    init {
        val type = ReflectionUtil.getParameterizedTypes(clazz)
        if (type == null || type.size != 1) {
            throw IllegalArgumentException("Class is not accepted: $type")
        }
        entityClass =
            ReflectionUtil.getClass(type[0]) ?: throw IllegalArgumentException("Entity class is not accepted: $type")
    }

    override fun invoke(proxy: Any?, method: Method?, args: Array<Any>?): Any? {
        if (method == null) {
            return null
        }
        println(method.name)
        val query = parseMethodName(method.name)
        println(query)
        val session = HibernateUtil.getSession()
        val tx = session.beginTransaction()
        when (query[0]) {
            "insert", "save" -> {
                if (args == null || args.isEmpty()) {
                    throw java.lang.IllegalArgumentException("Not enough arguments.");
                }
                session.saveOrUpdate(args[0])
            }
            "update" -> {
                if (args == null || args.isEmpty()) {
                    throw java.lang.IllegalArgumentException("Not enough arguments.");
                }
                session.update(args[0])
            }
            "delete", "remove" -> {
                if (args == null || args.isEmpty()) {
                    throw java.lang.IllegalArgumentException("Not enough arguments.");
                }
                session.delete(args[0])
            }
            "select", "find" -> {
                val q = parseSelectQuery(query.subList(1, query.size), session, args)
                val list = q.list()
                tx.commit()
                session.close()
                return if (list.isNotEmpty()) list[0] else null
            }
            "list" -> {
                val q = parseSelectQuery(query.subList(1, query.size), session, args)
                val list = q.list()
                tx.commit()
                session.close()
                return list
            }
        }
        tx.commit()
        session.close()
        return null
    }

    fun parseMethodName(str: String): List<String> {
        val list = LinkedList<String>()
        val buf = StringBuilder()
        for (s in str) {
            if (s.isUpperCase() && buf.isNotEmpty()) {
                list.add(buf.toString())
                buf.clear()
            }
            buf.append(s.toLowerCase())
        }
        if (buf.isNotEmpty()) {
            list.add(buf.toString())
        }
        return list
    }

    fun parseSelectQuery(query: List<String>, session: Session, args: Array<Any>?): Query<*> {
        val sb = StringBuilder("FROM ${entityClass.simpleName} ")
        val par = ArrayList<String>()
        val map = HashMap<Int, String>()
        val map2 = HashMap<Int, String>()
        for (str in query) {
            when (str) {
                "by" -> sb.append("WHERE ")
                "and" -> sb.append("AND ")
                "or" -> sb.append("OR ")
                "like" -> map[par.size - 1] = "LIKE :${par[par.size - 1]}"
                "contains" -> {
                    map[par.size - 1] = "LIKE :${par[par.size - 1]}"
                    map2[par.size - 1] = "%{0}%"
                }
                else -> {
                    sb.append("$str {${par.size}} ")
                    par.add(str)
                }
            }
        }
        if (par.isNotEmpty() && (args.isNullOrEmpty() || par.size < args.size)) {
            throw IllegalArgumentException("Not enough arguments")
        }
        var hql = sb.toString()
        println(hql)
        for (i in 0 until par.size) {
            hql = hql.replace("{$i}", map[i] ?: " = :{$par[i}")
        }
        println(hql)
        val q = session.createQuery(hql)
        for (i in 0 until par.size) {
            if (map2.containsKey(i)) {
                val parm = map2[i]!!.replace("{0}", args!![i].toString());
                println("${par[i]} -> $parm")
                q.setParameter(par[i], parm)
            } else {
                q.setParameter(par[i], args!![i])
            }
        }
        return q
    }
}