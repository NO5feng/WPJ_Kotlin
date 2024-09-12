package com.example.wpj_kotlin.components
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import com.example.wpj_kotlin.R
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@Composable
fun CommonDialog(
    titleText: String, contentText:String, leftBtnText:String = "取消", rightBtnText:String = "确定",
    leftBtnContainerColor: Color? = null, rightBtnContainerColor: ButtonColors? = null,
    leftBtnBorderColor: Color? = null, rightBtnBorderColor: Color? = null,
    onConfirm: () -> Unit, onCancel: () -> Unit, needBlank:Boolean = true
) {
    val context = LocalContext.current
    val pink = ContextCompat.getColor(context, R.color.pink)
    val white = ContextCompat.getColor(context, R.color.milk_white)
    val grey = ContextCompat.getColor(context, R.color.grey2)
    val content = if (needBlank) "    $contentText" else contentText

    val leftBtnColor = ButtonDefaults.buttonColors(
        containerColor = leftBtnContainerColor ?: Color(pink)
    )

    val rightBtnColor = ButtonDefaults.buttonColors(
        containerColor = leftBtnContainerColor ?: Color(white)
    )

    val leftBorderColor = leftBtnBorderColor ?: Color(pink)
    val rightBorderColor = rightBtnBorderColor ?: Color(pink)

    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        Column(
            modifier = Modifier
                .height(250.dp)
                .width(500.dp)
                .padding(start = 25.dp, end = 25.dp)
                .clip(
                    RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp)
                )
                .background(Color(white))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Text(text = titleText, style = MaterialTheme.typography.bodyLarge, fontSize = 23.sp)
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(0.8f)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = content, color = Color(grey),
                        modifier = Modifier
                            .padding(start = 20.dp, end = 15.dp, top = 10.dp)
                        )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxWidth()
                ) {
                    Row (
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Button(
                            colors = leftBtnColor,
                            border = BorderStroke(
                                width = 2.dp,
                                color = leftBorderColor,
                            ),
                            onClick = { onCancel()}
                        ) {
                            Text(text = leftBtnText, color = Color.White)
                        }
                        Button(
                            colors = rightBtnColor,
                            border = BorderStroke(
                                width = 2.dp,
                                color = rightBorderColor,
                            ),
                            onClick = { onConfirm() }
                        ) {
                            Text(text = rightBtnText, color = Color(pink))
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
fun rtyy() {
    WPJ_KotlinTheme {
        CommonDialog(
            onConfirm = {},
            onCancel = {},
            titleText = "标题",
            contentText = "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容？"
        )
    }
}

