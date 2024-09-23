package com.example.wpj_kotlin.utils
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import java.util.concurrent.atomic.AtomicLong

fun (() -> Unit).deBounce(delayMillis: Long = 2000L): () -> Unit {
    val lastClickTime = AtomicLong(0)
    return {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime.get() > delayMillis) {
            lastClickTime.set(currentTime)
            this()
        }
    }
}

fun getBitmapFromUri(context: Context, uri: Uri): Bitmap {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        // 对于 Android P（API 28）及以上版本，使用 ImageDecoder
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    } else {
        // 对于较低版本，使用 MediaStore 获取 Bitmap
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    }
}