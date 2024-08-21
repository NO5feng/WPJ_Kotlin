package com.example.wpj_kotlin.utils
import java.util.concurrent.atomic.AtomicLong

fun (() -> Unit).Debounce(delayMillis: Long = 2000L): () -> Unit {
    val lastClickTime = AtomicLong(0)
    return {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime.get() > delayMillis) {
            lastClickTime.set(currentTime)
            this()
        }
    }
}