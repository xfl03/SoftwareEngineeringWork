package me.xfl03.framework.repo

interface Repository<T> {
    fun insert(e: T)
    fun delete(e: T)
    fun update(e: T)
    fun selectById(id: Long): T?
    fun list(): List<T>
}