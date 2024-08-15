package com.example.wpj_kotlin.components
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.wpj_kotlin.utils.DateTimeUtils

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BirthDatePickerDialog(
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit,
    initDate: String
) {
    val context = LocalContext.current
    val pink = ContextCompat.getColor(context, R.color.pink)
    val white = ContextCompat.getColor(context, R.color.milk_white)

    val title = context.getString(R.string.dialog_datePick_title)
    val cancelBtn = context.getString(R.string.dialog_noSave_btn)
    val saveBtn = context.getString(R.string.dialog_Save_btn)
    val date = DateTimeUtils.DisassembleDateFormat(initDate)
    var year = remember { mutableIntStateOf(date.first).intValue }
    var month = remember { mutableIntStateOf(date.second).intValue }
    var day = remember { mutableIntStateOf(date.third).intValue }
    val yearSelectorState = rememberLazyListState(
        initialFirstVisibleItemIndex = DateTimeUtils.getYearsList().indexOf(year.toString())
    )
    val monthSelectorState = rememberLazyListState(
        initialFirstVisibleItemIndex = DateTimeUtils.getMonthsList().indexOf(month.toString())
    )
    val daySelectorState = rememberLazyListState(
        initialFirstVisibleItemIndex =
            DateTimeUtils.getDaysList(year, month).indexOf(day.toString())
    )

    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(250.dp)
                .width(500.dp)
                .padding(start = 25.dp, end = 25.dp)
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
                        .combinedClickable( onClick = {
                            onConfirm(DateTimeUtils.formatDate(year, month, day))
                        } )
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                ScrollSelector(
                    items = DateTimeUtils.getYearsList(),
                    state = yearSelectorState,
                    onItemSelected = { _, y -> year = y.toInt() },
                    selectedColor = Color(pink),
                    modifier = Modifier
                        .padding(start = 25.dp)
                )
                Text(text = "年")
                ScrollSelector(
                    items = DateTimeUtils.getMonthsList(),
                    state = monthSelectorState,
                    selectedColor = Color(pink),
                    onItemSelected = { _, m -> month = m.toInt() },
                )
                Text(text = "月")
                ScrollSelector(
                    items = DateTimeUtils.getDaysList(year, month),
                    state = daySelectorState,
                    selectedColor = Color(pink),
                    onItemSelected = { _, d -> day = d.toInt() },
                )
                Text(text = "日", modifier = Modifier.padding(end = 25.dp))
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun BrithDateDialogPreview() {
    WPJ_KotlinTheme {
        BirthDatePickerDialog(
            onConfirm = {},
            onCancel = {},
            initDate = "2024-8-15"
        )
    }
}

