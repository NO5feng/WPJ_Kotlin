package com.example.wpj_kotlin.ui

import Clock
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import com.example.wpj_kotlin.R
import com.example.wpj_kotlin.activity.ClockActivity
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import com.example.wpj_kotlin.components.ItemCard
import com.example.wpj_kotlin.database.database_item.ItemCard
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@SuppressLint("RememberReturnType", "ResourceAsColor")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainUi(
    onTextChanged: (String) -> Unit, onAddBtnClick: () -> Unit,
    slideStart: (Int) -> Unit, slideEnd: (Int) -> Unit,
    itemCards: List<ItemCard>
) {
    val context = LocalContext.current
    val backgroundColor = ContextCompat.getColor(context, R.color.yellow)
    val subBackgroundColor = ContextCompat.getColor(context, R.color.milk_white)
    val cursor = ContextCompat.getColor(context, R.color.pink)
    val topBarTitle = context.getString(R.string.home_title)
    val editText = context.getString(R.string.home_edit_hint)
    val addBtn = painterResource(id = R.drawable.add_button)

    var text by remember { mutableStateOf(TextFieldValue()) }

    val topAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color(backgroundColor),
        titleContentColor = Color.Black,
        scrolledContainerColor = Color(backgroundColor),
        navigationIconContentColor = Color(backgroundColor),
        actionIconContentColor = Color(backgroundColor)
    )

    // 闹钟动画 的 一些参数
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    // 获取屏幕宽高（单位：像素）
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }
    // 用于动画平移的 Anima table（x、y 坐标分别控制）
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    var isClicked by remember { mutableStateOf(false) }
    // 使用协程启动动画，可记住一个 scope
    val coroutineScope = rememberCoroutineScope()
    val iconColor by animateColorAsState(
        targetValue = if (isClicked)  Color(0xFFFFABBE) else Color.Black,
        animationSpec = tween(durationMillis = 600),
        label = "iconColorAnimation"
    )
    val iconSize by animateDpAsState(
        targetValue = if (isClicked) 90.dp else 33.dp,
        animationSpec = tween(durationMillis = 800),
        label = "iconSizeAnimation"
    )

    Box (modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(backgroundColor))
        ) {
            TopAppBar(
                title = {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = topBarTitle,
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentSize(Alignment.Center),
                        )
                    }
                },
                colors = topAppBarColors
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                    .background(Color(subBackgroundColor))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 50.dp)
                ) {
                    TextField(
                        value = text,
                        onValueChange = {
                            text = it
                            onTextChanged(it.text)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color(backgroundColor),
                            unfocusedContainerColor = Color(backgroundColor),
                            cursorColor = Color(cursor),
                            focusedLeadingIconColor = Color.Black,
                            unfocusedLeadingIconColor = Color.Black,
                            focusedPlaceholderColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Black
                        ),
                        placeholder = { Text(text = editText) },
                        singleLine = true, // 使输入框为单行
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .width(300.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 50.dp, topEnd = 50.dp,
                                    bottomStart = 50.dp, bottomEnd = 50.dp
                                )
                            )
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f) // Use weight to distribute the remaining space
                            .verticalScroll(rememberScrollState())
                    ) {
                        if (itemCards.isNotEmpty()) {
                            for (itemCard in itemCards ) {
                                ItemCard(
                                    itemCard = itemCard,
                                    slideStart = { i -> slideStart(i) },
                                    slideEnd = { i -> slideEnd(i) },
                                )
                            }
                        }
                    }

                    Image(
                        painter = addBtn,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .combinedClickable( onClick = { onAddBtnClick() } )
                    )
                }
            }
        }
        Icon(
            imageVector = Clock,
            contentDescription = "跳转闹钟",
            tint = iconColor,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 50.dp, end = 25.dp)
                .size(iconSize)
                .offset {
                    IntOffset(
                        x = offsetX.value.roundToInt(),
                        y = offsetY.value.roundToInt()
                    )
                }
                .clickable {
                    isClicked = !isClicked
                    val initialX = offsetX.value
                    val initialY = offsetY.value

                    // 计算图标尺寸及屏幕中央的目标坐标（居中对齐，需要减去图标宽度的一半）
                    val iconSizePx = with(density) { 33.dp.toPx() }
                    val targetX = (screenWidthPx - iconSizePx) / 2
                    val targetY = (screenHeightPx - iconSizePx) / 2

                    coroutineScope.launch {
                        val toCenterX = async {
                            offsetX.animateTo(
                                -targetX +183,
                                animationSpec = tween(
                                    durationMillis = 600,
                                    easing = LinearOutSlowInEasing
                                )
                            )
                        }
                        val toCenterY = async {
                            offsetY.animateTo(
                                targetY,
                                animationSpec = tween(
                                    durationMillis = 600,
                                    easing = LinearOutSlowInEasing
                                )
                            )
                        }
                        toCenterX.await()
                        toCenterY.await()
                        val intent = Intent(context, ClockActivity::class.java)
                        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity)
                        ActivityCompat.startActivity(context, intent, options.toBundle())

                        delay(1000)
                        val backX = async {
                            offsetX.animateTo(
                                initialX,
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = LinearOutSlowInEasing
                                )
                            )
                        }
                        val backY = async {
                            offsetY.animateTo(
                                initialY,
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = LinearOutSlowInEasing
                                )
                            )
                        }
                        isClicked = !isClicked
                    }
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    WPJ_KotlinTheme {
        val sampleItemCards = listOf(
            ItemCard(itemName = "花花", type = 1, day = "5", id = 0),
            ItemCard(itemName = "鸡蛋", type = 2, day = "10", id = 1)
        )
        MainUi(
            onTextChanged = {},
            onAddBtnClick = {},
            slideStart = {},
            slideEnd = {},
            itemCards = sampleItemCards
        )
    }
}