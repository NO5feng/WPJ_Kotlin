package com.example.wpj_kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import com.example.wpj_kotlin.database.NewItemViewModelFactory
import com.example.wpj_kotlin.database.database_item.Item
import com.example.wpj_kotlin.database.database_item.ItemCard
import com.example.wpj_kotlin.ui.MainUi
import com.example.wpj_kotlin.utils.DateTimeUtils
import com.example.wpj_kotlin.utils.switchTimesTamp
import com.example.wpj_kotlin.viewModels.NewItemViewModel
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    private val viewModel: NewItemViewModel by viewModels {
        NewItemViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WPJ_KotlinTheme {
                val itemCards = remember { mutableStateOf<List<ItemCard>>(emptyList()) }
                DisposableEffect(Unit) {
                    val observer = LifecycleEventObserver { _, event ->
                        if (event == Lifecycle.Event.ON_START) {
                            // 当生命周期进入 onStart 时，重新加载数据
                            viewModel.getAllItem { items ->
                                itemCards.value = getItemCards(items)
                            }
                        }
                    }

                    // 注册生命周期观察者
                    lifecycle.addObserver(observer)

                    // 在 Composable 销毁时移除观察者
                    onDispose {
                        lifecycle.removeObserver(observer)
                    }
                }
                MainUi(
                    onTextChanged = { text ->
                        Log.d("MainActivity", "Text changed: $text")
                    },
                    onImageClick = {
                        startActivity(Intent(this, AddItemActivity::class.java))
                    },
                    itemCards = itemCards.value
                )
            }
        }
    }

    fun getItemCards(items: List<Item>? = null): List<ItemCard> {
        val currentTime = DateTimeUtils.getCurrentTime().switchTimesTamp()

        return items?.groupBy { it.itemName }?.flatMap { (itemName, itemList) ->
            itemList.map { item ->
                val expiredTime = item.expiredDate.switchTimesTamp()
                val type = if (expiredTime >= currentTime) 1 else 2
                val dayDifference = abs(((expiredTime - currentTime) / (60 * 60 * 24))).toString()
                ItemCard(
                    itemName = itemName,
                    type = type,
                    day = dayDifference
                )
            }
        } ?: emptyList()
    }
}
