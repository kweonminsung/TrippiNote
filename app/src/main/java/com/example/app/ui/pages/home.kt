package com.example.app.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.app.PreselectedPin
import com.example.app.R
import com.example.app.TabType
import com.example.app.ui.components.home.HomeTripButton
import com.example.app.ui.components.map.MapPinType
import com.example.app.ui.components.popup.AddTripForm
import com.example.app.ui.theme.CustomColors
import com.example.app.util.DatetimeUtil
import com.example.app.util.database.MapRepository
import com.example.app.util.database.model
import com.google.android.gms.maps.model.LatLng

@Composable
fun HomeTab(
    setTabType: (TabType) -> Unit,
    setPreselectedPin: (PreselectedPin?) -> Unit,
) {
    val context = LocalContext.current

    val (plannedTrip, setPlannedTrip) = remember {
        mutableStateOf(MapRepository.getPlannedTrip(context))
    }
    val (allTrips, setAllTrips) = remember { mutableStateOf(MapRepository.getTrips(context)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = CustomColors.LighterGray
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 30.dp,
                    vertical = 16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (plannedTrip != null) {
                Column(
                    modifier = Modifier
                        .clickable {
                            setTabType(TabType.MAP)
                            setPreselectedPin(PreselectedPin(
                                type = MapPinType.TRIP,
                                id = plannedTrip.id,
                                position = LatLng(plannedTrip.lat, plannedTrip.lng)
                            ))
                        }
                ) {
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
                    )
                    Text(
                        text = "${DatetimeUtil.dateToDotDate(plannedTrip.start_date as String)}${if (plannedTrip.end_date != null) " - ${DatetimeUtil.dateToDotDate(plannedTrip.end_date)}" else ""}",
                        color = CustomColors.Gray,
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )

                    if (plannedTrip.end_date != null) {
                        Text(
                            text = "${DatetimeUtil.daysBetween(plannedTrip.start_date, plannedTrip.end_date)} days",
                            color = CustomColors.Gray,
                            modifier = Modifier
                                .padding(top = 4.dp)
                        )
                    }
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

            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "나의 여행",
                    fontSize = 20.sp,
                    color = CustomColors.Black,
                )
                AddTripForm(
                    button = { onClick ->
                        IconButton(onClick = onClick) {
                            Icon(Icons.Default.Add,
                                contentDescription = "추가",
                                tint = CustomColors.Black,
                            )
                        }
                    },
                    saveFn = { title, start_date, end_date, locValue ->
                        MapRepository.createTrip(context, model.Trip(
                            id = -1,
                            title = title,
                            start_date = start_date,
                            end_date = end_date,
                            lat = locValue.position.latitude,
                            lng = locValue.position.longitude,
                            created_at = DatetimeUtil.getCurrentDatetime(),
                        ))

                        setAllTrips(MapRepository.getTrips(context))
                        setPlannedTrip(MapRepository.getPlannedTrip(context))
                    },
                    title = "새 여행 추가",
                )
            }

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .width(300.dp)
                    .background(color = CustomColors.White),
            ) {
                if (allTrips.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "여행 기록이 없습니다",
                            color = CustomColors.DarkGray,
                            fontSize = 18.sp
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
                            onClick = {
                                setTabType(TabType.MAP)
                                val tripInfo = MapRepository.getTripById(context, trip.id)
                                setPreselectedPin(PreselectedPin(
                                    type = MapPinType.TRIP,
                                    id = trip.id,
                                    position = if(tripInfo != null) LatLng(tripInfo.lat, tripInfo.lng) else null
                                ))
                            }
                        )
                    }
                }
            }
        }
    }
}