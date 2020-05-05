package me.xfl03.framework.view

@Target(AnnotationTarget.PROPERTY)
@Retention(value = AnnotationRetention.RUNTIME)
annotation class Order(val value: Int)