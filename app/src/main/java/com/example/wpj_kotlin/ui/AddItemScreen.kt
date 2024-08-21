package com.example.wpj_kotlin.ui

import android.annotation.SuppressLint
import android.graphics.Rect
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.wpj_kotlin.R
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import com.example.wpj_kotlin.utils.Debounce

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddItemUI(
    // 回调方法
    onCancelClick: () -> Unit, onSaveClick: () -> Unit, onTextChanged: (String) -> Unit,
    onSwitch: (Boolean) -> Unit, onBirthDateClick: () -> Unit, onExpiredDateClick: () -> Unit,
    onAddImageClick: () -> Unit,

    // 传值
    manufactureDateTextValue: String, expiredDateTextValue: String, switchState: Boolean
    ) {
    val context = LocalContext.current
    val yellow = ContextCompat.getColor(context, R.color.yellow)
    val white = ContextCompat.getColor(context, R.color.milk_white)
    val pink = ContextCompat.getColor(context, R.color.pink)
    val grey = ContextCompat.getColor(context, R.color.grey)
    val topBarTitle = context.getString(R.string.add_title)
    val editTitle = context.getString(R.string.add_input)
    val manufactureDateTitle = context.getString(R.string.manufacture_date)
    val expiredDateTitle = context.getString(R.string.expired_date)
    val remindTitle = context.getString(R.string.add_remind)
    val addImageTitle = context.getString(R.string.add_src_input)
    val image = painterResource(id = R.drawable.image)
    val height = Rect().top + 20
    val currentText = remember { mutableStateOf(TextFieldValue()) }
    val debouncedClick = remember { onAddImageClick.Debounce() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(white))
            .statusBarsPadding()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = height.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                modifier = Modifier
                    .size(27.dp)
                    .combinedClickable(onClick = { onCancelClick() })
            )

            Text(
                text = topBarTitle,
                fontSize = 18.sp
            )

            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                modifier = Modifier
                    .size(27.dp)
                    .combinedClickable(onClick = { onSaveClick() })
            )
        }

        BasicTextField(
            value = currentText.value,
            onValueChange = {
                currentText.value = it
                onTextChanged(it.toString())
            },
            singleLine = true,
            textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
            cursorBrush = SolidColor(Color(pink)),
            decorationBox = { innerTextField ->
                Column {
                    Box(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .padding(start = 15.dp, bottom = 5.dp)
                    ) {
                        if (currentText.value.text.isEmpty()) {
                            Text(text = editTitle, fontSize = 18.sp, color = Color(grey))
                        }
                        innerTextField()
                    }
                    Divider(
                        color = Color(grey),
                        thickness = 1.dp)
                }
            },
            modifier = Modifier
                .padding(top = 50.dp)
                .width(320.dp)
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(200.dp)
                .width(320.dp)
                .padding(top = 50.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 10.dp, topEnd = 10.dp,
                        bottomStart = 10.dp, bottomEnd = 10.dp
                    )
                )
                .background(Color(yellow))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .combinedClickable( onClick = { onBirthDateClick() } )
                ) {
                    Text(
                        text = manufactureDateTitle,
                        color = Color(grey),
                        modifier = Modifier
                            .padding(start = 20.dp)
                    )
                    Text(
                        text = manufactureDateTextValue,
                        color = Color(grey),
                        modifier = Modifier
                            .padding(end = 20.dp)
                    )
                }
                Divider(
                    color = Color(white),
                    thickness = 1.dp)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .combinedClickable( onClick = { onExpiredDateClick() } )
                ) {
                    Text(
                        text = expiredDateTitle,
                        color = Color(grey),
                        modifier = Modifier
                            .padding(start = 20.dp)
                    )
                    Text(
                        text = expiredDateTextValue,
                        color = Color(grey),
                        modifier = Modifier
                            .padding(end = 20.dp)
                    )
                }
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(90.dp)
                .width(320.dp)
                .padding(top = 30.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 10.dp, topEnd = 10.dp,
                        bottomStart = 10.dp, bottomEnd = 10.dp
                    )
                )
                .background(Color(yellow))
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                Text(text = remindTitle, fontSize = 18.sp, color = Color(grey))
                Switch(
                    checked = switchState,
                    onCheckedChange = { isChecked ->
                        onSwitch(isChecked)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(pink),
                        uncheckedThumbColor = Color(yellow),
                        uncheckedTrackColor = Color.White,
                        uncheckedBorderColor = Color(grey)
                    ),
                    modifier = Modifier
                        .height(40.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .height(80.dp)
                .width(320.dp)
                .padding(top = 30.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 10.dp, topEnd = 10.dp,
                        bottomStart = 10.dp, bottomEnd = 10.dp
                    )
                )
                .background(Color(yellow))
                .combinedClickable( onClick = { debouncedClick() } )
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(top = 13.dp)
            ) {
                Icon(
                    painter = image,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .height(25.dp)
                        .width(100.dp)
                )
                Text(
                    text = addImageTitle,
                    fontSize = 18.sp, color = Color(grey),
                    modifier = Modifier
                        .padding(start = 10.dp, top = 1.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WPJ_KotlinTheme {
        AddItemUI(
            onCancelClick = {},
            onSaveClick = {},
            onTextChanged = {},
            onBirthDateClick = {},
            onExpiredDateClick = {},
            onSwitch = {},
            onAddImageClick = {},
            manufactureDateTextValue = "2024-08-08",
            expiredDateTextValue = "2024-09-08",
            switchState = false
        )
    }
}
