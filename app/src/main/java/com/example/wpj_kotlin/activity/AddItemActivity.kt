package com.example.wpj_kotlin.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.wpj_kotlin.R
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import com.example.wpj_kotlin.components.BirthDatePickerDialog
import com.example.wpj_kotlin.components.ExpiredDatePickerDialog
import com.example.wpj_kotlin.components.ImagePickerBottomSheet
import com.example.wpj_kotlin.components.RemindPickerDialog
import com.example.wpj_kotlin.database.NewItemViewModelFactory
import com.example.wpj_kotlin.database.database_item.Item
import com.example.wpj_kotlin.ui.AddItemUI
import com.example.wpj_kotlin.utils.DateTimeUtils
import com.example.wpj_kotlin.utils.createImageUri
import com.example.wpj_kotlin.utils.getBitmapFromFile
import com.example.wpj_kotlin.utils.getBitmapFromUri
import com.example.wpj_kotlin.utils.getRealPathFromUri
import com.example.wpj_kotlin.utils.saveBitmapToFile
import com.example.wpj_kotlin.utils.switchTimesTamp
import com.example.wpj_kotlin.viewModels.NewItemViewModel

class AddItemActivity : ComponentActivity() {
    private val viewModel: NewItemViewModel by viewModels {
        NewItemViewModelFactory(application)
    }
    @SuppressLint("CoroutineCreationDuringComposition")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedId = intent.getIntExtra("selectedId", -1)
        if ( selectedId != -1) { viewModel.reviseDataById(selectedId) }

        enableEdgeToEdge()
        setContent {
            WPJ_KotlinTheme {
                var imageUri: Uri? = null
                val context = LocalContext.current
                val showBirthDialog = remember { mutableStateOf(false) }
                val showRemindDialog = remember { mutableStateOf(false) }
                val showExpiredDialog = remember { mutableStateOf(false) }
                val showImageBottomSheet = remember { mutableStateOf(false) }
                val cameraPermissionGranted = remember { mutableStateOf(false) }
                val storagePermissionGranted = remember { mutableStateOf(false) }

                val imagePath = viewModel.imagePath.value
                val selectedName = viewModel.itemName.value
                val switchState = viewModel.switchState.value
                val selectedBirthDate = viewModel.birthDate.value
                val selectedExpiredDate = viewModel.expiredDate.value

                val cameraLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.TakePicture()
                ) { success ->
                    if (success) {
                        imageUri?.let {
                            val filePath = getRealPathFromUri(context, it)
                            if (filePath != null) {
                                viewModel.updateImagePath(filePath)
                            }
                        }
                    }
                }

                val storagePermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    storagePermissionGranted.value = isGranted
                    if (isGranted) {
                        imageUri = createImageUri(context)
                        imageUri?.let { uri -> cameraLauncher.launch(uri) }
                    } else {
                        Toast.makeText(context, "存储权限被拒绝", Toast.LENGTH_SHORT).show()
                    }
                }

                val cameraPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    cameraPermissionGranted.value = isGranted
                    if (isGranted) {
                        storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    } else {
                        Toast.makeText(context, "相机权限被拒绝", Toast.LENGTH_SHORT).show()
                    }
                }

                fun checkAndRequestPermissions() {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        cameraPermissionGranted.value = true
                        storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    } else {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }

                // 照片选择器
                val singleLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                    onResult = { uri ->
                        uri?.let {
                            val bitmap = getBitmapFromUri(context, it)
                            bitmap.let { bmp ->
                                val savedFilePath = saveBitmapToFile(context, bmp)
                                viewModel.updateImagePath(savedFilePath.toString())
                            }
                        }
                    }
                )

                AddItemUI(
                    onCancelClick = { onBackPressed() },
                    onSaveClick = {
                        val newItem = Item(
                            itemName = selectedName,
                            birthDate = selectedBirthDate,
                            expiredDate = selectedExpiredDate,
                            remindDate = if (switchState) viewModel.remindDate.value else null,
                            imagePath = imagePath
                        )
                        onBackPressed()
                        if (selectedId == -1) {
                            viewModel.insertItem(newItem)
                            Toast.makeText(this, getText(R.string.toast_add_item_success), Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.fixItem(selectedId, newItem)
                            Toast.makeText(this, getText(R.string.toast_fix_item_success), Toast.LENGTH_SHORT).show()
                        }

                    },
                    onTextChanged = { name -> viewModel.updateItemName(name) },
                    onBirthDateClick = { showBirthDialog.value = true },
                    onExpiredDateClick = { showExpiredDialog.value = true },
                    onSwitch = { check ->
                        viewModel.updateCheck(check)
                        if (check) { showRemindDialog.value = true }},
                    onAddImageClick = {
                        showImageBottomSheet.value = true
                    },
                    manufactureDateTextValue = selectedBirthDate,
                    expiredDateTextValue = selectedExpiredDate,
                    switchState = switchState,
                    itemName = selectedName,
                    bitmap = imagePath?.let { getBitmapFromFile(it) },
                )

                if (showBirthDialog.value) {
                    BirthDatePickerDialog(
                        onConfirm = { date ->
                            showBirthDialog.value = false
                            viewModel.updateBirthDate(date)
                        },
                        onCancel = { showBirthDialog.value = false },
                        initDate = selectedBirthDate
                    )
                }

                if (showExpiredDialog.value) {
                    ExpiredDatePickerDialog(
                        onConfirm = { s ->
                            val date = DateTimeUtils.getExpiredDate(selectedBirthDate.switchTimesTamp(), s)
                            viewModel.updateExpiredDate(date)
                            showExpiredDialog.value = false
                        },
                        onCancel = { showExpiredDialog.value = false }
                    )
                }

                if (showRemindDialog.value) {
                    RemindPickerDialog(
                        onConfirm = { type ->
                            viewModel.updateCheck(true)
                            viewModel.updateRemindDate(DateTimeUtils.getRemindDate(selectedBirthDate.switchTimesTamp(), type))
                            showRemindDialog.value = false
                        },
                        onCancel = {
                            viewModel.updateCheck(false)
                            showRemindDialog.value = false
                        }
                    )
                }

                if (showImageBottomSheet.value) {
                    ImagePickerBottomSheet(
                        onConfirm = { type ->
                            when (type) {
                                1 -> checkAndRequestPermissions()
                                2 -> singleLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            }
                            showImageBottomSheet.value = false
                        },
                        onCancel = {
                            showImageBottomSheet.value = false
                        }
                    )
                }
            }
        }
    }
}

