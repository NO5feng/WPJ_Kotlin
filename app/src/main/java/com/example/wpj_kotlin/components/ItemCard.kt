package com.example.wpj_kotlin.components
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.wpj_kotlin.R
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun ItemCard() {
    val context = LocalContext.current
    val pink = ContextCompat.getColor(context, R.color.pink)
    val yellow = ContextCompat.getColor(context, R.color.yellow)

    val archive = SwipeAction(
        icon = rememberVectorPainter(Icons.Filled.Create),
        background = Color.White,
        onSwipe = {}
    )
    val snooze = SwipeAction(
        icon = rememberVectorPainter(Icons.Filled.Delete),
        background = Color(pink),
        isUndo = true,
        onSwipe = { },
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 15.dp, topEnd = 15.dp,
                        bottomStart = 15.dp, bottomEnd = 15.dp
                    )
                )
                .background(Color.White)
        ) {
            SwipeableActionsBox(
                startActions = listOf(archive),
                endActions = listOf(snooze)
            ){
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .weight(0.7f)
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = "芝麻油",
                            modifier = Modifier
                                .padding(start = 20.dp)
                        )
                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(0.3f)
                            .fillMaxHeight()
                            .background(Color(pink))
                    ) {
                        Text(
                            text = "164",
                            modifier = Modifier
                                .padding(top = 5.dp)
                        )
                        Text(text = "天")
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
        ItemCard()
    }
}