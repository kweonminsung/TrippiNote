package com.example.app.ui.pages.album

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.app.ui.components.search_bar.SearchBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import com.example.app.util.database.model
import com.example.app.util.database.MapRepository.getTripById
import com.example.app.util.database.MapRepository.getRegionById
import com.example.app.util.database.MapRepository.getScheduleById

@Composable
fun FolderNavigatorScreen(context: Context) {
    var selectedTrip by remember { mutableStateOf<model.Trip?>(null) }
    var selectedRegion by remember { mutableStateOf<model.Region?>(null) }
    var selectedSchedule by remember { mutableStateOf<model.Schedule?>(null) }

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
            ScheduleImageGrid(
                context = context,
                schedule = selectedSchedule!!,
                onBack = { selectedSchedule = null }
            )
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
            .padding(16.dp)
    ) {

        FolderNavigatorScreen(
            context = context
        )

    }
}