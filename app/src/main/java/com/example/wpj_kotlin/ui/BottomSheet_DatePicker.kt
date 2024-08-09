package com.example.wpj_kotlin.ui
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import com.example.wpj_kotlin.R
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import com.sd.lib.compose.wheel_picker.FVerticalWheelPicker

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BrithDatePickerDialog(
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

    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(250.dp)
                .width(500.dp)
                .padding(start = 30.dp, end = 30.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 15.dp, topEnd = 15.dp,
                        bottomStart = 15.dp, bottomEnd = 15.dp
                    )
                )
                .background(Color(white))
        ) {
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
                        .combinedClickable( onClick = { onCancel() } )
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                FVerticalWheelPicker(
                    modifier = Modifier.width(60.dp),
                    // Specified item count.
                    count = 50,
                ) { index ->
                    Text(index.toString())
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

    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(250.dp)
                .width(500.dp)
                .padding(start = 30.dp, end = 30.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 15.dp, topEnd = 15.dp,
                        bottomStart = 15.dp, bottomEnd = 15.dp
                    )
                )
                .background(Color(white))
        ) {
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
                        .combinedClickable( onClick = { onCancel() } )
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {


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

    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(250.dp)
                .width(500.dp)
                .padding(start = 30.dp, end = 30.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 15.dp, topEnd = 15.dp,
                        bottomStart = 15.dp, bottomEnd = 15.dp
                    )
                )
                .background(Color(white))
        ) {
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
                        .combinedClickable( onClick = { onCancel() } )
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BrithDateDialogPreview() {
    WPJ_KotlinTheme {
        BrithDatePickerDialog(
            onConfirm = {},
            onCancel = {}
        )
    }
}

