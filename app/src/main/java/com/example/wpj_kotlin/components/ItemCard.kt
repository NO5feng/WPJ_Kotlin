package com.example.wpj_kotlin.components
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.core.content.ContextCompat
import com.example.wpj_kotlin.R
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import com.example.wpj_kotlin.database.database_item.ItemCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun ItemCard(
    slideStart: (Int) -> Unit, slideEnd: (Int) -> Unit,
    itemCard: ItemCard
) {
    val context = LocalContext.current
    val pink = ContextCompat.getColor(context, R.color.pink)
    val yellow = ContextCompat.getColor(context, R.color.yellow)
    val color = if (itemCard.type == 1) pink else yellow
    val topText = if (itemCard.type == 1) itemCard.day else context.getString(R.string.overdue)
    val bottomText = if (itemCard.type == 1) context.getString(R.string.day) else itemCard.day + context.getString(R.string.day)

    var big by remember { mutableStateOf(false) }
    val height = if (big) 350.dp else 80.dp
    var isVisible by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    var lastClickTime by remember { mutableLongStateOf(0L) }
    val transitionProgress by animateFloatAsState(
        targetValue = if (!big) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        finishedListener = { if (big) isVisible = false }
    )
    val offsetX: Dp = if (itemCard.itemName.length <= 2) {
        (1 - transitionProgress) * 141.dp
    } else if (itemCard.itemName.length <= 3) {
        (1 - transitionProgress) * 132.dp
    } else if (itemCard.itemName.length <= 4) {
        (1 - transitionProgress) * 123.dp
    } else if (itemCard.itemName.length <= 5) {
        (1 - transitionProgress) * 113.dp
    } else if (itemCard.itemName.length <= 6) {
        (1 - transitionProgress) * 104.dp
    } else if (itemCard.itemName.length <= 7) {
        (1 - transitionProgress) * 95.dp
    } else {
        (1 - transitionProgress) * 80.dp
    }

    val archive = SwipeAction(
        icon = rememberVectorPainter(Icons.Filled.Create),
        background = Color.White,
        onSwipe = { slideStart(itemCard.id) }
    )
    val snooze = SwipeAction(
        icon = rememberVectorPainter(Icons.Filled.Delete),
        background = Color(color),
        isUndo = true,
        onSwipe = { slideEnd(itemCard.id) },
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp)
                .clip( RoundedCornerShape(15.dp,15.dp,15.dp,15.dp) )
                .background(Color.White)
                .focusable()
                .clickable {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastClickTime >= 800) {
                        lastClickTime = currentTime
                        big = !big
                        if (!big) {
                            isVisible = !isVisible
                        }
                        coroutineScope.launch {
                            delay(100)
                        }
                    }
                }
        ) {
            SwipeableActionsBox(
                startActions = listOf(archive),
                endActions = listOf(snooze)
            ){
                Row(
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth()
                ) {
                    if (isVisible) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .weight(0.7f)
                                .fillMaxHeight()
                        ) {
                            Text(
                                text = if (itemCard.itemName.length > 7) AnnotatedString(itemCard.itemName.substring(0, 7) + "...").toString() else itemCard.itemName,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .offset(x = offsetX)

                            )
                        }
                    } else {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .weight(0.7f)
                                .fillMaxHeight()
                        ) {
                            AnimatedVisibility(
                                visible = big,
                                enter = slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = 200)),
                                exit = slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(durationMillis = 200))
                            ) {
                                Text( text = itemCard.itemName )
                            }
                        }
                    }
                    if (!big) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ){
                            Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
                        }
                    }

                    AnimatedVisibility(
                        visible = !big,
                        enter = slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(durationMillis = 200)),
                        exit = slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(durationMillis = 200)),
                        modifier = Modifier
                            .weight(0.3f)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(color))
                        ) {
                            Text(
                                text = topText,
                                modifier = Modifier
                                    .padding(top = 5.dp)
                            )
                            Text(text = bottomText)
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun xxx() {
    WPJ_KotlinTheme {
        val x = ItemCard(itemName = "芝麻油", type = 1, day = "110", id=0)
        ItemCard(
            itemCard = x,
            slideEnd = {},
            slideStart = {}
        )
    }
}