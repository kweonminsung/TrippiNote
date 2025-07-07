package com.example.app.ui.pages

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.example.app.R
import com.example.app.ui.components.home.HomeTripButton
import com.example.app.ui.theme.CustomColors
import com.example.app.util.DatetimeUtil
import com.example.app.util.database.MapRepository
import com.example.app.util.database.model

@Composable
fun HomeTab() {
    val plannedTrip = MapRepository.getPlannedTrip(LocalContext.current)
//    val plannedTrip = null as model.Trip?
    val allTrips = MapRepository.getTrips(LocalContext.current)
//    val allTrips = null as List<model.Trip>?

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = CustomColors.LighterGray
            ),
        contentAlignment = Alignment.TopCenter  // Box 내의 콘텐츠를 상단 중앙 정렬
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (plannedTrip != null) {
                Image(
                    painter = painterResource(id = R.drawable.sample_trip),
                    contentDescription = "",
                    modifier = Modifier
                        .width(300.dp)
                        .height(170.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(8.dp),
                        )
                        .background(CustomColors.White),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = plannedTrip.title,
                    fontSize = 20.sp,
                    color = CustomColors.Black,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.Start)
                )
                Text(
                    text = "${DatetimeUtil.dateToDotDate(plannedTrip.start_date as String)}${if (plannedTrip.end_date != null) " - ${DatetimeUtil.dateToDotDate(plannedTrip.end_date)}" else ""}",
                    color = CustomColors.Gray,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.Start)
                )

                if (plannedTrip.end_date != null) {
                    Text(
                        text = "${DatetimeUtil.daysBetween(plannedTrip.start_date, plannedTrip.end_date)} days",
                        color = CustomColors.Gray,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .align(Alignment.Start)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(170.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(8.dp),
                        )
                        .border(
                            width = 1.dp,
                            color = CustomColors.Gray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(CustomColors.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "예정된 여행이 없습니다",
                        color = CustomColors.DarkGray,
                        fontSize = 18.sp,
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "나의 여행",
                fontSize = 20.sp,
                color = CustomColors.Black,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.Start)
            )
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = CustomColors.White),
            ) {
                if (allTrips.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "여행 기록이 없습니다",
                            color = CustomColors.DarkGray,
                            fontSize = 18.sp,
                        )
                    }
                } else {
                    allTrips.forEach { trip ->
                        HomeTripButton(
                            title = trip.title,
                            subtitle = if (trip.start_date == null && trip.end_date == null) {
                                "날짜 정보 없음"
                            } else {
                                "${trip.start_date?.let { DatetimeUtil.dateToDotDate(it) }}${if (trip.end_date != null) " - ${DatetimeUtil.dateToDotDate(trip.end_date)}" else ""}"
                            },
                            imageId = "",
                            onClick = { /* TODO: Navigate to trip details */ }
                        )
                    }
                }
            }
        }
    }
}

fun getPlannedTrip(context: Context): model.Trip? {
    return MapRepository.getPlannedTrip(context)
}

fun getAllTrips(context: Context): List<model.Trip> {
    return MapRepository.getTrips(context)
}