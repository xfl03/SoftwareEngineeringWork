package me.xfl03.framework.repo

interface Repository<T> {
    fun save(e: T)
    fun delete(e: T)
    fun update(e: T)
    fun findById(id: Long): T?
    fun list(): List<T>
}