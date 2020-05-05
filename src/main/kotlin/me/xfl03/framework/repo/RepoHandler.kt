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
            "insert" -> {
                if (args == null || args.isEmpty()) {
                    throw java.lang.IllegalArgumentException("Not enough arguments.");
                }
                session.save(args[0])
            }
            "update" -> {
                if (args == null || args.isEmpty()) {
                    throw java.lang.IllegalArgumentException("Not enough arguments.");
                }
                session.update(args[0])
            }
            "delete" -> {
                if (args == null || args.isEmpty()) {
                    throw java.lang.IllegalArgumentException("Not enough arguments.");
                }
                session.delete(args[0])
            }
            "find", "select" -> {
                val q = parseSelectQuery(query.subList(1, query.size), session, args)
                val list = q.list()
                tx.commit()
                session.close()
                return if (list.isNotEmpty()) list[0] else null
            }
            "list" -> {
                val q = parseSelectQuery(query.subList(1, query.size), session, args)
                tx.commit()
                session.close()
                return q.list()
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
        for (str in query) {
            when (str) {
                "by" -> sb.append("WHERE ")
                "and" -> sb.append("AND ")
                "or" -> sb.append("OR ")
                else -> {
                    sb.append("$str = :$str ")
                    par.add(str)
                }
            }
        }
        if (par.isNotEmpty() && (args.isNullOrEmpty() || par.size < args.size)) {
            throw IllegalArgumentException("Not enough arguments")
        }
        println(sb.toString())
        val q = session.createQuery(sb.toString())
        for (i in 0 until par.size) {
            q.setParameter(par[i], args!![i])
        }
        return q
    }
}