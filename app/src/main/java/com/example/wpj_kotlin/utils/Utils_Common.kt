package com.example.wpj_kotlin.utils
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileOutputStream
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

fun getBitmapFromFile(filePath: String): Bitmap? {
    return try {
        val file = File(filePath)
        if (!file.exists()) {
            Log.e("BitmapError", "File does not exist")
        }
        BitmapFactory.decodeFile(filePath)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getRealPathFromUri(context: Context, uri: Uri): String? {
    // Handle different URI types
    if (uri.scheme == "content") {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            if (cursor.moveToFirst()) {
                return cursor.getString(columnIndex)
            }
        }
    } else if (uri.scheme == "file") {
        return uri.path // 直接返回文件路径
    }
    return null
}

fun saveBitmapToFile(context: Context, bitmap: Bitmap): String? {
    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "WPJ_IMG_${System.currentTimeMillis()}.jpg")
    return try {
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            file.absolutePath
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun createImageUri(context: Context): Uri? {
    val resolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/WPG")
    }
    return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}