package com.example.wpj_kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import com.example.wpj_kotlin.database.NewItemViewModelFactory
import com.example.wpj_kotlin.ui.MainUi
import com.example.wpj_kotlin.viewModels.NewItemViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: NewItemViewModel by viewModels {
        NewItemViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WPJ_KotlinTheme {
                val itemCards = viewModel.itemCards
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
                        Log.d("MainActivity", "Text changed: $text")
                    },
                    onAddBtnClick = { startActivity(Intent(this, AddItemActivity::class.java)) },
                    slideStart = {

                    },
                    slideEnd = { i -> viewModel.deleteItemById(i) },
                    itemCards = itemCards.value
                )
            }
        }
    }

}
