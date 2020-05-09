package me.xfl03.framework.view

import javafx.application.Platform
import tornadofx.View
import java.util.*

object ViewManager {
    val views = LinkedList<View>()
    var flag = true

    fun add(view: View) {
        views.offerLast(view)
    }

    fun display(view: View) {
        val lastView = views.peekLast()
        views.offerLast(view)
        lastView.replaceWith(view)
    }

    fun displayNew(view: View) {
        flag = false
        val lastView = views.peekLast()
        views.clear()
        views.offerLast(view)
        lastView.close()
        view.openWindow()
    }

    fun back() {
        val lastView = views.pollLast()
        lastView.replaceWith(views.peekLast())
    }

    fun tryExit() {
        if (flag) {
            Platform.exit()
        }
        flag = true
    }
}