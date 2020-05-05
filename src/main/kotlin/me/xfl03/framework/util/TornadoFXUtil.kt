package me.xfl03.framework.util


import javafx.beans.property.SimpleStringProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import me.xfl03.framework.view.BooleanAdapter
import me.xfl03.framework.view.Name
import me.xfl03.framework.view.Order
import tornadofx.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties


object TornadoFXUtil {
    fun initDI() {
        FX.dicontainer = object : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>) = GuiceUtil.injector!!.getInstance(type.java)
        }
    }

    fun addChangeListener(text: TextField, listener: (String) -> Unit) {
        val executor = DebounceExecutor(300)
        text.textProperty()
            .addListener { _, _, str -> executor.execute { listener.invoke(str) } }
    }

    fun <T> addDoubleClickListener(table: TableView<T>, listener: (T) -> Unit) {
        table.setOnMousePressed {
            if (it.isPrimaryButtonDown && it.clickCount == 2) {
                val node: Node = (it.target as Node).parent
                val row: TableRow<T>? = if (node is TableRow<*>) {
                    node as TableRow<T>
                } else if (node.parent is TableRow<*>) {
                    node.parent as TableRow<T>
                } else {
                    null
                }
                if (row != null) {
                    listener.invoke(row.item)
                }
            }
        }
    }

    fun showConfirm(title: String, msg: String, onSuccess: () -> Unit) {
        val alert = Alert(Alert.AlertType.CONFIRMATION)
        alert.title = title
        alert.headerText = msg

        val result = alert.showAndWait()
        if (result.isPresent && result.get() == ButtonType.OK) {
            onSuccess.invoke()
        }
    }

    fun showAlert(title: String, msg: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = msg

        alert.show()
    }

    fun <T : Any> createTableView(arr: List<T>): TableView<T> {
        if (arr.isNullOrEmpty()) {
            return TableView<T>()
        }
        return createTableView(arr, arr[0].javaClass.kotlin)
    }

    fun <T : Any> createTableView(arr: List<T>, clazz: KClass<T>): TableView<T> {
        val tv = TableView<T>()
        tv.items = arr.asObservable()
        sortProperty(clazz.memberProperties).forEach { property ->
            val name = getName(property)
            val field = property.name
            when (property.returnType.toString()) {
                "kotlin.Int" -> tv.column<T, Int>(name, field)
                "kotlin.Long" -> tv.column<T, Long>(name, field)
                "kotlin.String" -> tv.column<T, String>(name, field)
                "kotlin.Boolean" -> {
                    val adapter = property.findAnnotation<BooleanAdapter>()
                    if (adapter != null) {
                        tv.column(name) { it: TableColumn.CellDataFeatures<T, String> ->
                            SimpleStringProperty(if (property.get(it.value) as Boolean) adapter.t else adapter.f)
                        }
                    }
                }
                else -> tv.column(name) { it: TableColumn.CellDataFeatures<T, String> ->
                    SimpleStringProperty(property.get(it.value).toString())
                }
            }
        }
        return tv
    }

    fun <T : Any> createForm(obj: T, now: View? = null, pre: View? = null): VBox {
        val vbox = VBox()
        val clazz = obj.javaClass.kotlin;
        sortProperty(clazz.memberProperties).forEach { property ->
            val hbox = HBox()
            val name = getName(property)
            val label = Label(name)
            val textField = when (property.returnType.toString()) {
                "kotlin.Boolean" -> {
                    val adapter = property.findAnnotation<BooleanAdapter>()
                    if (adapter != null) {
                        TextField(if (property.get(obj) as Boolean) adapter.t else adapter.f)
                    } else {
                        TextField(property.get(obj).toString())
                    }
                }
                else -> TextField(property.get(obj).toString())
            }
            //label.labelFor = textField

            textField.maxWidth = 150.0

            val spacer = Pane()
            HBox.setHgrow(spacer, Priority.ALWAYS)
            spacer.setMinSize(10.0, 1.0)

            hbox += label
            hbox += spacer
            hbox += textField
            vbox += hbox
        }

        val btnL = Button("»∑»œ")
        val spacer = Pane()
        HBox.setHgrow(spacer, Priority.ALWAYS)
        spacer.setMinSize(10.0, 1.0)
        val btnR = Button("∑µªÿ")
        val hbox = HBox()
        hbox += btnL
        hbox += spacer
        hbox += btnR
        vbox += hbox

        if (now != null && pre != null) {
            btnL.onAction = EventHandler {
                now.replaceWith(pre)
            }
            btnR.onAction = EventHandler { now.replaceWith(pre) }
        }

        return vbox
    }

    private fun <T> sortProperty(pros: Collection<KProperty1<T, *>>): Collection<KProperty1<T, *>> {
        return pros.sortedBy { it.findAnnotation<Order>()?.value ?: 0 }
    }

    private fun <T> getName(property: KProperty1<T, *>): String {
        return property.findAnnotation<Name>()?.value ?: property.name
    }
}