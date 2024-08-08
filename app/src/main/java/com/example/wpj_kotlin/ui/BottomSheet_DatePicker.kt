package com.example.wpj_kotlin.ui
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import com.example.wpj_kotlin.R
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import java.util.*

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BrithDatePickerDialog(
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit
) {
//    var selectedYear by remember { mutableStateOf(initialDate.get(Calendar.YEAR)) }
//    var selectedMonth by remember { mutableStateOf(initialDate.get(Calendar.MONTH)) }
//    var selectedDay by remember { mutableStateOf(initialDate.get(Calendar.DAY_OF_MONTH)) }
    val context = LocalContext.current
    val pink = ContextCompat.getColor(context, R.color.pink)
    val grey = ContextCompat.getColor(context, R.color.grey)
    val white = ContextCompat.getColor(context, R.color.milk_white)

    val title = context.getString(R.string.dialog_datePick_title)
    val cancelBtn = context.getString(R.string.dialog_noSave_btn)
    val saveBtn = context.getString(R.string.dialog_Save_btn)

    ModalBottomSheet(
        onDismissRequest = onCancel,
        containerColor = Color(white)
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)

        ) {
            Column{
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    Text(
                        text = cancelBtn,
                        modifier = Modifier
                            .combinedClickable( onClick = { onCancel() } )
                    )
                    Text(text = title)
                    Text(
                        text = saveBtn,
                        color = Color(pink),
                        modifier = Modifier
                            .combinedClickable( onClick = { onConfirm("") } )

                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExpiredDatePickerDialog(
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    val pink = ContextCompat.getColor(context, R.color.pink)
    val grey = ContextCompat.getColor(context, R.color.grey)
    val white = ContextCompat.getColor(context, R.color.milk_white)

    val title = context.getString(R.string.dialog_timeout_title)
    val cancelBtn = context.getString(R.string.dialog_noSave_btn)
    val saveBtn = context.getString(R.string.dialog_Save_btn)

    ModalBottomSheet(
        onDismissRequest = onCancel,
        containerColor = Color(white)
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
        ) {
            Column{
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    Text(
                        text = cancelBtn,
                        modifier = Modifier
                            .combinedClickable( onClick = { onCancel() } )
                    )
                    Text(text = title)
                    Text(
                        text = saveBtn,
                        color = Color(pink),
                        modifier = Modifier
                            .combinedClickable( onClick = { onConfirm("") } )

                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RemindPickerDialog(
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    val pink = ContextCompat.getColor(context, R.color.pink)
    val grey = ContextCompat.getColor(context, R.color.grey)
    val white = ContextCompat.getColor(context, R.color.milk_white)

    val title = context.getString(R.string.dialog_remind_title)
    val cancelBtn = context.getString(R.string.dialog_noSave_btn)
    val saveBtn = context.getString(R.string.dialog_Save_btn)

    ModalBottomSheet(
        onDismissRequest = onCancel,
        containerColor = Color(white)
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
        ) {
            Column{
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    Text(
                        text = cancelBtn,
                        modifier = Modifier
                            .combinedClickable( onClick = { onCancel() } )
                    )
                    Text(text = title)
                    Text(
                        text = saveBtn,
                        color = Color(pink),
                        modifier = Modifier
                            .combinedClickable( onClick = { onConfirm("") } )

                    )
                }
            }
        }
    }
}

/*
由于 使用三方插件 Android Studio 没办法渲染ui 不方便调试 弄个这个 专门用了调试 UI 然后粘到上面的类中
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UiTest(
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    val pink = ContextCompat.getColor(context, R.color.pink)
    val grey = ContextCompat.getColor(context, R.color.grey)
    val white = ContextCompat.getColor(context, R.color.milk_white)

    val title = context.getString(R.string.dialog_datePick_title)
    val cancelBtn = context.getString(R.string.dialog_noSave_btn)
    val saveBtn = context.getString(R.string.dialog_Save_btn)

    var pickerValue by remember { mutableStateOf(0) }

    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .background(Color(white))

        ) {
            Column{
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    Text(
                        text = cancelBtn,
                        modifier = Modifier
                            .combinedClickable( onClick = { onCancel() } )
                    )
                    Text(text = title)
                    Text(
                        text = saveBtn,
                        color = Color(pink),
                        modifier = Modifier
                            .combinedClickable( onClick = { onConfirm("") } )

                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BrithDateDialogPreview() {
    WPJ_KotlinTheme {
        UiTest(
            onConfirm = {},
            onCancel = {}
        )
    }
}

