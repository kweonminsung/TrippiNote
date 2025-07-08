package com.example.app.ui.pages.album

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.example.app.ui.components.buttons.BottomButton
import com.example.app.util.database.model
import com.example.app.util.database.MapRepository.getTripById
import com.example.app.util.database.MapRepository.getRegionById
import com.example.app.util.database.MapRepository.getScheduleById
import java.io.File
import java.io.FileOutputStream

@Composable
fun FolderNavigatorScreen(context: Context) {
    var selectedTrip by remember { mutableStateOf<model.Trip?>(null) }
    var selectedRegion by remember { mutableStateOf<model.Region?>(null) }
    var selectedSchedule by remember { mutableStateOf<model.Schedule?>(null) }
    var selectedImageFileId by remember { mutableStateOf<String?>(null) }
    var showPopup by remember { mutableStateOf(false) }

    when {
        selectedTrip == null -> {
            TripFolderGridColumn(
                context = context,
                onTripFolderClick = { tripImageResult ->
                    val trip = getTripById(context, tripImageResult.trip_id!!)
                    selectedTrip = trip
                }
            )
        }
        selectedRegion == null -> {
            RegionFolderGridColumn(
                context = context,
                trip = selectedTrip!!,
                onRegionFolderClick = { regionImageResult ->
                    val region = getRegionById(context, regionImageResult.region_id!!)
                    selectedRegion = region
                }
            )
        }
        selectedSchedule == null -> {
            ScheduleFolderGridColumn(
                context = context,
                region = selectedRegion!!,
                onScheduleFolderClick = { scheduleImageResult ->
                    val schedule = getScheduleById(context, scheduleImageResult.schedule_id!!)
                    selectedSchedule = schedule }
            )
        }
        else -> {
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                uri?.let {
                    val inputStream = context.contentResolver.openInputStream(it)
                    val tempFile = File(context.cacheDir, "selected_image.jpg")
                    inputStream?.use { input ->
                        FileOutputStream(tempFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                    val schedule = selectedSchedule!!

                    UploadImage(context, tempFile, schedule)
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                ){
                ScheduleImageGrid(
                    context = context,
                    schedule = selectedSchedule!!,
                    onImageClick = {   image ->
                        selectedImageFileId = image.file_id
                        showPopup = true
                    }
                )


                BottomButton(
                    label = "사진 업로드  \uD83D\uDCF8",
                    onClick = {
                        launcher.launch("image/*")
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .width(200.dp)
                        .padding(top = 16.dp, bottom = 8.dp)
                )

                if (showPopup && selectedImageFileId != null) {
                    ImagePopup(
                        context = context,
                        fileId = selectedImageFileId!!,
                        onDismiss = { showPopup = false }
                    )
                }
            }

        }
    }
}

@Composable
fun AlbumTab() {

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,  // 클릭 효과 제거
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()  // 👉 키보드 내리기
            }
    ) {

        FolderNavigatorScreen(
            context = context
        )

    }
}