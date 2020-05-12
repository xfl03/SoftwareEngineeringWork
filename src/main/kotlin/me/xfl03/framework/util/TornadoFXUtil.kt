package me.xfl03.framework.util


import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import me.xfl03.framework.view.*
import tornadofx.*
import java.sql.Date
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
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

    fun <T> addSingleClickListener(table: TableView<T>, listener: (T) -> Unit) {
        table.setOnMousePressed {
            if (it.isPrimaryButtonDown && it.clickCount == 1) {
                val item=getSelected<T>(it)
                if (item != null) {
                    listener.invoke(item)
                }
            }
        }
    }

    fun <T> addDoubleClickListener(table: TableView<T>, listener: (T) -> Unit) {
        table.setOnMousePressed {
            if (it.isPrimaryButtonDown && it.clickCount == 2) {
                val item=getSelected<T>(it)
                if (item != null) {
                    listener.invoke(item)
                }
            }
        }
    }

    fun <T> getSelected(it: MouseEvent): T? {
        var node: Node? = it.target as Node
        var ret: TableRow<T>? = null
        while (node != null) {
            if (node is TableRow<*>) {
                ret = node as TableRow<T>
                break
            }
            node = node.parent
        }
        return ret?.item
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

    fun showWarn(title: String, msg: String) {
        val alert = Alert(Alert.AlertType.WARNING)
        alert.title = title
        alert.headerText = msg

        alert.show()
    }

    fun <T : Any> createTableView(arr: List<T>, manager: AdapterManager? = null): TableView<T> {
        if (arr.isNullOrEmpty()) {
            return TableView<T>()
        }
        return createTableView(arr, arr[0].javaClass.kotlin, manager)
    }

    fun <T : Any> createTableView(arr: List<T>, clazz: KClass<T>, manager: AdapterManager? = null): TableView<T> {
        val tv = TableView<T>()
        tv.items = arr.asObservable()
        sortProperty(clazz.memberProperties).forEach { property ->
            if (property.name == "password") {
                return@forEach
            }
            val name = getName(property)
            val field = property.name
            when (property.returnType.toString()) {
                "kotlin.Int" -> {
                    val adapter = property.findAnnotation<NumberAdapter>()
                    if (manager != null && adapter != null) {
                        tv.column(name) { it: TableColumn.CellDataFeatures<T, String> ->
                            SimpleStringProperty(manager.getText(adapter.value, property.get(it.value) as Number))
                        }
                    } else {
                        tv.column<T, Int>(name, field)
                    }
                }
                "kotlin.Long" -> {
                    val adapter = property.findAnnotation<NumberAdapter>()
                    if (manager != null && adapter != null) {
                        tv.column(name) { it: TableColumn.CellDataFeatures<T, String> ->
                            SimpleStringProperty(manager.getText(adapter.value, property.get(it.value) as Number))
                        }
                    } else {
                        tv.column<T, Long>(name, field)
                    }
                }
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

    fun <T : Any> createForm(obj: T, action: (T) -> Unit = {}): VBox {
        val vbox = VBox()
        val clazz = obj.javaClass.kotlin
        val map = HashMap<KProperty1<T, *>, StringProperty>()
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
            map[property] = textField.textProperty()
            //label.labelFor = textField
            label += textField

            textField.maxWidth = 200.0

            val spacer = Pane()
            HBox.setHgrow(spacer, Priority.ALWAYS)
            spacer.setMinSize(10.0, 1.0)

            hbox += label
            hbox += spacer
            hbox += textField
            vbox += hbox
        }

        val btnL = Button("确认")
        val spacer = Pane()
        HBox.setHgrow(spacer, Priority.ALWAYS)
        spacer.setMinSize(10.0, 1.0)
        val btnR = Button("返回")
        val hbox = HBox()
        hbox += btnL
        hbox += spacer
        hbox += btnR
        vbox += hbox

        btnL.setOnAction {
            try {
                action.invoke(getResult(obj, map))
                ViewManager.back()
            } catch (e: Exception) {
                println(e.toString())
                showWarn("保存失败", "请检查输入是否正确")
            }
        }
        btnR.setOnAction { ViewManager.back() }

        return vbox
    }

    private fun <T : Any> getResult(obj: T, map: Map<KProperty1<T, *>, StringProperty>): T {
        for (e in map) {
            val property = e.key
            val text = e.value.value ?: ""
            println(property)
            println(property is KMutableProperty1<T, *>)
            if (property !is KMutableProperty1<T, *>) {
                println("'${property.name}' is val.")
                continue
            }
            when (property.returnType.toString()) {
                "kotlin.Int" -> (property as KMutableProperty1<T, Int>).set(obj, text.toInt())
                "kotlin.Long" -> (property as KMutableProperty1<T, Long>).set(obj, text.toLong())
                "kotlin.String" -> (property as KMutableProperty1<T, String>).set(obj, text)
                "kotlin.Boolean" -> {
                    val adapter = property.findAnnotation<BooleanAdapter>()
                    if (adapter != null) {
                        (property as KMutableProperty1<T, Boolean>).set(obj, text == adapter.t)
                    }
                }
                "java.sql.Date" -> {
                    (property as KMutableProperty1<T, Date>).set(obj, Date.valueOf(text))
                }
                "else" -> println("Type not supported: ${property.returnType}")
            }
        }
        return obj
    }

    fun <T : Any> createInfo(obj: T, manager: AdapterManager? = null): VBox {
        val vbox = VBox()
        val clazz = obj.javaClass.kotlin
        val map = HashMap<KProperty1<T, *>, StringProperty>()
        sortProperty(clazz.memberProperties).forEach { property ->
            if (property.name == "password") {
                return@forEach
            }
            val hbox = HBox()
            val name = getName(property)
            val label = Label(name)

            val textField = when (property.returnType.toString()) {
                "kotlin.Int", "kotlin.Long" -> {
                    val adapter = property.findAnnotation<NumberAdapter>()
                    if (manager != null && adapter != null) {
                        Label(manager.getText(adapter.value, property.get(obj) as Number))
                    } else {
                        Label(property.get(obj).toString())
                    }
                }
                "kotlin.Boolean" -> {
                    val adapter = property.findAnnotation<BooleanAdapter>()
                    if (adapter != null) {
                        Label(if (property.get(obj) as Boolean) adapter.t else adapter.f)
                    } else {
                        Label(property.get(obj).toString())
                    }
                }
                else -> Label(property.get(obj).toString())
            }
            map[property] = textField.textProperty()
            //label.labelFor = textField
            label += textField

            textField.maxWidth = 200.0

            val spacer = Pane()
            HBox.setHgrow(spacer, Priority.ALWAYS)
            spacer.setMinSize(10.0, 1.0)

            hbox += label
            hbox += spacer
            hbox += textField
            vbox += hbox
        }

        val btnR = Button("返回")
        vbox += btnR

        btnR.setOnAction { ViewManager.back() }

        return vbox
    }

    private fun <T> sortProperty(pros: Collection<KProperty1<T, *>>): Collection<KProperty1<T, *>> {
        return pros.sortedBy { it.findAnnotation<Order>()?.value ?: 0 }
    }

    private fun <T> getName(property: KProperty1<T, *>): String {
        return property.findAnnotation<Name>()?.value ?: property.name
    }
}