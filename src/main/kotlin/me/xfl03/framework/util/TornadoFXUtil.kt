package me.xfl03.framework.util


import java.sql.Date

import javafx.scene.control.TableView
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TableColumn

import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.KProperty1

import me.xfl03.framework.view.Name
import me.xfl03.framework.view.BooleanAdapter
import me.xfl03.framework.view.Order

import tornadofx.asObservable
import tornadofx.column

@ExperimentalStdlibApi
object TornadoFXUtil {
    fun <T : Any> createTableView(arr: List<T>, clazz: KClass<T>): TableView<T> {
        val tv = TableView<T>()
        tv.items = arr.asObservable()
        sortProperty(clazz.memberProperties).forEach { property ->
            var name = property.name
            if (property.hasAnnotation<Name>()) {
                name = property.findAnnotation<Name>()!!.value
            }
            var field = property.name
            println(property.returnType)
            when (property.returnType.toString()) {
                "kotlin.Int" -> tv.column<T, Int>(name, field)
                "kotlin.Long" -> tv.column<T, Long>(name, field)
                "kotlin.String" -> tv.column<T, String>(name, field)
                "kotlin.Boolean" -> {
                    if (property.hasAnnotation<BooleanAdapter>()) {
                        val adapter = property.findAnnotation<BooleanAdapter>()!!
                        tv.column(name) { it: TableColumn.CellDataFeatures<T, String> ->
                            SimpleStringProperty(if (property.get(it.value) as Boolean) adapter.t else adapter.f)
                        }
                    }
                }
                "java.sql.Date" -> tv.column<T, Date>(name, field)
            }
        }
        return tv
    }

    fun <T> sortProperty(pros: Collection<KProperty1<T, *>>): Collection<KProperty1<T, *>> {
        return pros.sortedBy { if (it.hasAnnotation<Order>()) it.findAnnotation<Order>()!!.value else 0 }
    }
}