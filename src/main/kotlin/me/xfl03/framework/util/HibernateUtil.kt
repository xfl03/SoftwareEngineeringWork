package me.xfl03.framework.util

import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

object HibernateUtil {
    lateinit var factory: SessionFactory
    fun getSession(): Session = factory.openSession()
    fun init() {
        factory = Configuration().configure().buildSessionFactory()
    }
}