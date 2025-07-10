package com.example.app.ui.pages.album

import android.content.Context
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.app.LocalSession
import com.example.app.ui.components.buttons.BottomButton
import com.example.app.ui.theme.CustomColors
import com.example.app.util.database.ImageResult
import com.example.app.util.database.model
import com.example.app.util.database.MapRepository.getTripById
import com.example.app.util.database.MapRepository.getRegionById
import com.example.app.util.database.MapRepository.getScheduleById
import java.io.File
import java.io.FileOutputStream
import com.example.app.util.database.MapRepository.getScheduleImages
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider

@Composable
fun FolderNavigatorScreen(context: Context, username: String = "사용자") {
    var selectedTrip by remember { mutableStateOf<model.Trip?>(null) }
    var selectedRegion by remember { mutableStateOf<model.Region?>(null) }
    var selectedSchedule by remember { mutableStateOf<model.Schedule?>(null) }
    var selectedImageFileId by remember { mutableStateOf<String?>(null) }
    var imageList by remember { mutableStateOf<List<ImageResult>>(emptyList()) }

    var showPopup by remember { mutableStateOf(false) }



    when {
        selectedTrip == null -> {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = CustomColors.LighterGray),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(64.dp),
                    contentAlignment = Alignment.Center
                ){
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "${username}의 여행 사진  \uD83C\uDF04",
                        color = CustomColors.Black,
                        modifier = Modifier,
                        fontSize = 20.sp
                    )
                }
                TripFolderGridColumn(
                    context = context,
                    onTripFolderClick = { tripImageResult ->
                        val trip = getTripById(context, tripImageResult.trip_id!!)
                        selectedTrip = trip
                    }
                )
            }
        }
        selectedRegion == null -> {
            val trip = selectedTrip!!
            BackHandler( onBack = { selectedTrip = null } )
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = CustomColors.LighterGray)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = CustomColors.LighterGray),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 10.dp)
                            .height(64.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${username}의 여행 다이어리",
                            color = CustomColors.Black,
                            modifier = Modifier,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = trip.title,
                            color = CustomColors.Black,
                            modifier = Modifier,
                            fontSize = 19.sp
                        )
                    }
                    RegionFolderGridColumn(
                        context = context,
                        trip = selectedTrip!!,
                        onRegionFolderClick = { regionImageResult ->
                            val region = getRegionById(context, regionImageResult.region_id!!)
                            selectedRegion = region
                        }
                    )
                }
                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp),
                    onClick = {
                        selectedTrip = null
                    },
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(CustomColors.LighterGray)
                            .padding(4.dp),
                        tint = CustomColors.Black
                    )
                }
            }
        }
        selectedSchedule == null -> {
            val trip = selectedTrip!!
            val region = selectedRegion!!
            BackHandler( onBack = { selectedRegion = null } )
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = CustomColors.LighterGray)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = CustomColors.LighterGray),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(64.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = trip.title,
                            color = CustomColors.Black,
                            modifier = Modifier,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = region.title,
                            color = CustomColors.Black,
                            modifier = Modifier,
                            fontSize = 19.sp
                        )
                    }
                    ScheduleFolderGridColumn(
                        context = context,
                        region = selectedRegion!!,
                        onScheduleFolderClick = { scheduleImageResult ->
                            val schedule =
                                getScheduleById(context, scheduleImageResult.schedule_id!!)
                            selectedSchedule = schedule
                        }
                    )
                }
                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp),
                    onClick = {
                        selectedRegion = null
                    },
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(CustomColors.LighterGray)
                            .padding(4.dp),
                        tint = CustomColors.Black
                    )
                }
            }
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

                    uploadImage(context, tempFile, schedule)
                    val updatedSchedule = getScheduleById(context, schedule.id)
                    selectedSchedule = updatedSchedule
                    imageList = getScheduleImages(context,updatedSchedule!!.id)
                }
            }

            val region = selectedRegion!!
            val schedule = selectedSchedule!!

            imageList = getScheduleImages(context, schedule.id)

            BackHandler( onBack = { selectedSchedule = null } )
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = CustomColors.LighterGray)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = CustomColors.LighterGray),
                    horizontalAlignment = Alignment.CenterHorizontally,
                )
                {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(64.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = region.title,
                            color = CustomColors.Black,
                            modifier = Modifier,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = schedule.title,
                            color = CustomColors.Black,
                            modifier = Modifier,
                            fontSize = 19.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        ScheduleImageGrid(
                            context = context,
                            schedule_images = imageList,
                            onImageClick = { image ->
                                selectedImageFileId = image.file_id
                                showPopup = true
                            }
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
                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp),
                    onClick = {
                        selectedSchedule = null
                    },
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(CustomColors.LighterGray)
                            .padding(4.dp),
                        tint = CustomColors.Black
                    )
                }

                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    onClick = {
                        launcher.launch("image/*")
                    },
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Image",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(CustomColors.LighterGray)
                            .padding(4.dp),
                        tint = CustomColors.Black
                    )
                }


//                BottomButton(
//                    label = "사진 업로드 \uD83C\uDFDE\uFE0F",
//                    onClick = {
//                        launcher.launch("image/*")
//                    },
//                    modifier = Modifier
//                        .align(Alignment.BottomCenter)
//                        .width(200.dp)
//                        .padding(top = 16.dp, bottom = 16.dp)
//                )


            }
        }
    }
}

@Composable
fun AlbumTab() {

    val context = LocalContext.current
    val sessionData = LocalSession.current.value
    val username = sessionData.user.name

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = CustomColors.LighterGray)
    ) {
        FolderNavigatorScreen(
            context = context,
            username = username
        )
    }
}
