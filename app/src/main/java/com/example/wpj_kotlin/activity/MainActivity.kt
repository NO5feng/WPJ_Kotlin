package com.example.wpj_kotlin.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.wpj_kotlin.R
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import com.example.wpj_kotlin.components.CommonDialog
import com.example.wpj_kotlin.database.NewItemViewModelFactory
import com.example.wpj_kotlin.ui.MainUi
import com.example.wpj_kotlin.viewModels.NewItemViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: NewItemViewModel by viewModels {
        NewItemViewModelFactory(application)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WPJ_KotlinTheme {
                val context = LocalContext.current
                val content = context.getString(R.string.dialog_delete_content)

                val itemCards = viewModel.itemCards
                val imagePage = remember { mutableStateOf("") }
                val showDeleteDialog = remember { mutableStateOf(false) }
                val selectedItemId = remember { mutableStateOf<Int?>(null) }
                LaunchedEffect(Unit) {
                    viewModel.getAllItem()  // 在启动时加载数据
                }
                DisposableEffect(Unit) {
                    val observer = LifecycleEventObserver { _, event ->
                        if (event == Lifecycle.Event.ON_START) {
                            // 当生命周期进入 onStart 时，重新加载数据
                            viewModel.getAllItem()
                        }
                    }

                    // 注册生命周期观察者
                    lifecycle.addObserver(observer)

                    // 在 Composable 销毁时移除观察者
                    onDispose { lifecycle.removeObserver(observer) }
                }
                MainUi(
                    onTextChanged = { text ->
                        Log.d("MainActivity", "搜索框: $text")
                        viewModel.getAllItem(text)
                    },
                    onAddBtnClick = { startActivity(Intent(this, AddItemActivity::class.java)) },
                    slideStart = { i ->
                        val intent = Intent(this, AddItemActivity::class.java)
                        intent.putExtra("selectedId", i)
                        startActivity(intent)
                    },
                    slideEnd = { i ->
                        selectedItemId.value = i
                        showDeleteDialog.value = true
                     },
                    itemCards = itemCards.value
                )

                if (showDeleteDialog.value) {
                    CommonDialog(
                        onConfirm = {
                            showDeleteDialog.value = false
                            selectedItemId.value?.let { id ->
                                viewModel.deleteItemById(id)
                            }
                        },
                        onCancel = { showDeleteDialog.value = false },
                        titleText = "🤨🤨🤨",
                        contentText = content,
                    )
                }
            }
        }
    }

}
