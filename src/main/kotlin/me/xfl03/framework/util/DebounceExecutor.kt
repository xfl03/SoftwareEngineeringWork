package me.xfl03.framework.util

import java.util.*

class DebounceExecutor(private val delay: Long) {
    private var timer = Timer()
    private var timerTask: TimerTask? = null
    fun execute(runnable: () -> Unit) {
        timerTask?.cancel()
        timerTask = object : TimerTask() {
            override fun run() {
                runnable.invoke()
            }
        }
        timer.schedule(timerTask, delay)
    }
}