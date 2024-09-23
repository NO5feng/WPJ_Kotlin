package com.example.wpj_kotlin.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class PermissionHelper(private val activity: ComponentActivity) {

    // 启动器用于请求权限
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    // 初始化，传递处理权限请求结果的回调函数
    fun initialize(onPermissionResult: (Boolean) -> Unit) {
        permissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            // 当用户响应权限请求时触发
            onPermissionResult(isGranted)
        }
    }

    // 检查是否已经授予指定的权限
    fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
    }

    // 请求权限
    fun requestPermission(permission: String) {
        if (!isPermissionGranted(permission)) {
            permissionLauncher.launch(permission)
        }
    }

    // 检查并请求相机权限
    fun requestCameraPermission(onPermissionResult: (Boolean) -> Unit) {
        initialize(onPermissionResult)
        requestPermission(Manifest.permission.CAMERA)
    }

    // 检查并请求存储权限（可选：针对Android 10以下设备）
    fun requestStoragePermission(onPermissionResult: (Boolean) -> Unit) {
        initialize(onPermissionResult)
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}