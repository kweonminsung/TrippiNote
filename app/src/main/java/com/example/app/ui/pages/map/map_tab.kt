package com.example.app.ui.pages.map

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.app.ui.components.BOTTOM_DRAWER_ANIMATION_DURATION
import com.example.app.ui.components.BottomDrawer
import com.example.app.ui.components.search_bar.SearchBar
import com.example.app.ui.components.map.MapPin
import com.example.app.ui.components.map.CustomPin
import com.example.app.ui.components.map.CustomTransportPin
import com.example.app.ui.components.map.MapPinType
import com.example.app.ui.components.map.RegionInfoButton
import com.example.app.ui.components.map.ScheduleInfoButton
import com.example.app.ui.components.map.TransportInfoButton
import com.example.app.ui.components.map.TransportPin
import com.example.app.ui.theme.CustomColors
import com.example.app.util.DatetimeUtil
import com.example.app.util.database.MapRepository
import com.example.app.util.database.model
import com.example.app.util.database.model.TransportType
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

//val INITIAL_LAT_LNG = LatLng(36.3730, 127.3622) // 지도의 초기 위치(카이스트)
val INITIAL_LAT_LNG = LatLng(48.866096757760225, 2.348085902631283) // 지도의 초기 위치(파리)
val INITIAL_ZOOM_LEVEL = ZOOM_LEVEL.CONTINENT // 초기 줌 레벨

object ZOOM_LEVEL {
    const val CONTINENT = 3f   // 대륙 수준
    const val COUNTRY = 6f     // 나라 수준
    const val CITY = 12f       // 도시 수준
}


@Composable
fun MapTab() {
    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    var mapQuery by remember { mutableStateOf("") }
    var searchJob: Job? = remember { null }
    var searchResults by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }

    // 카메라 위치 상태
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(INITIAL_LAT_LNG, INITIAL_ZOOM_LEVEL)
    }

    var cameraTarget by remember { mutableStateOf<LatLng?>(null) } // 카메라 이동용 상태

    var zoomLevel by remember { mutableStateOf(INITIAL_ZOOM_LEVEL) } // 줌 레벨 상태

    var sessionMapPins by remember { mutableStateOf<List<MapPin>>(emptyList()) } // 세션에 저장된 핀 목록
    var sessionTransportPin by remember { mutableStateOf<List<TransportPin>>(emptyList()) } // 세션에 저장된 교통수단 핀 목록
    var userSelectedMapPins by remember { mutableStateOf<List<MapPin>>(emptyList()) } // 유저가 선택한 핀 목록

    var selectedTripId by remember { mutableStateOf<Int?>(null) } // 선택된 여행 ID
    var selectedRegionId by remember { mutableStateOf<Int?>(null) } // 선택된 지역 ID

    var tripInfoBottomDrawerState by remember { mutableStateOf(false) } // 여행 정보 Bottom Drawer 상태
    var regionInfoBottomDrawerState by remember { mutableStateOf(false) } // 지역 정보 Bottom Drawer 상태

    // 카메라 이동은 여기서
    LaunchedEffect(cameraTarget) {
        cameraTarget?.let {
            val update = CameraUpdateFactory.newLatLngZoom(it, cameraPositionState.position.zoom)
            cameraPositionState.animate(update)
        }
    }

    // 줌 레벨 변경 감지
    LaunchedEffect(zoomLevel) {
        Log.d(
            "MapTab", "Zoom level changed to: ${
                when (zoomLevel) {
                    ZOOM_LEVEL.CONTINENT -> "Continent"
                    ZOOM_LEVEL.COUNTRY -> "Country"
                    ZOOM_LEVEL.CITY -> "City"
                    else -> "Unknown"
                }
            }"
        )
        // 줌 레벨에 따라 세션에서 핀 목록 가져오기
        sessionMapPins = MapTabData.getSessionPinsByZoomRate(context, zoomLevel)

        // 교통수단 핀 목록도 업데이트
        sessionTransportPin = when (zoomLevel) {
            ZOOM_LEVEL.CITY -> MapTabData.getSessionTransportPinsByZoomRate(context, zoomLevel)
            else -> emptyList()
        }
    }

    // 줌 정도 실시간 감지
    LaunchedEffect(cameraPositionState) {
        snapshotFlow { cameraPositionState.position.zoom }
            .distinctUntilChanged()
            .collectLatest { zoomRate ->
//                Log.d("MapTab", "Zoom rate changed: $zoomRate")
                zoomLevel = when {
                    zoomRate >= ZOOM_LEVEL.CITY -> ZOOM_LEVEL.CITY
                    zoomRate >= ZOOM_LEVEL.COUNTRY -> ZOOM_LEVEL.COUNTRY
                    else -> ZOOM_LEVEL.CONTINENT
                }

            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                compassEnabled = false,  // 나침반 비활성화
                myLocationButtonEnabled = false, // 내 위치 버튼 비활성화
                mapToolbarEnabled = false, // 지도 툴바 비활성화

            ),
            onMapClick = { latLng ->
                focusManager.clearFocus() // 키보드 내리기

                // 핀이 있으면 삭제, 없으면 새로 추가
                if (userSelectedMapPins.isNotEmpty()) {
                    userSelectedMapPins = emptyList()
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        val locationInfo = PlaceUtil.getLocationInfo(context, latLng)
                        Log.d(
                            "MapTab",
                            "Clicked location: $latLng, Title: ${locationInfo.title}, Snippet: ${locationInfo.snippet}"
                        )
                        val pinInfo = MapPin(
                            id = null,
                            type = MapPinType.USER_SELECTED,
                            position = latLng,
                            title = locationInfo.title,
                            subtitle = locationInfo.snippet
                        )
                        userSelectedMapPins = listOf(pinInfo)
                    }
                }
            }
        ) {
            // 세션에 저장된 핀 목록
            sessionMapPins.forEach { pin ->
                key("${pin.position.latitude},${pin.position.longitude},${pin.title}") {
                    MarkerComposable(
                        state = MarkerState(position = pin.position),
                        zIndex = 2f,
                        onClick = {
                            focusManager.clearFocus() // 키보드 내리기

//                            Log.d("MapTab", "Clicked pin: ${pin.title} at ${pin.position}")

                            if (pin.type == MapPinType.TRIP) {
                                regionInfoBottomDrawerState = false
                                if (selectedTripId != pin.id) {
                                    tripInfoBottomDrawerState = false
                                }
                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(BOTTOM_DRAWER_ANIMATION_DURATION.toLong()) // BottomDrawer가 닫히는 애니메이션 대기
                                    tripInfoBottomDrawerState = true
                                }
                                selectedTripId = pin.id
                            } else if (pin.type == MapPinType.REGION) {
                                tripInfoBottomDrawerState = false
                                if (selectedRegionId != pin.id) {
                                    regionInfoBottomDrawerState = false
                                }
                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(BOTTOM_DRAWER_ANIMATION_DURATION.toLong()) // BottomDrawer가 닫히는 애니메이션 대기
                                    regionInfoBottomDrawerState = true
                                }
                                selectedRegionId = pin.id
                            } else {
                                val regionId = (MapRepository.getScheduleById(context, pin.id as Int) as model.Schedule).region_id
                                if (regionId != selectedRegionId) {
                                    tripInfoBottomDrawerState = false
                                    regionInfoBottomDrawerState = false
                                }
                                selectedRegionId = regionId

                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(BOTTOM_DRAWER_ANIMATION_DURATION.toLong()) // BottomDrawer가 닫히는 애니메이션 대기
                                    regionInfoBottomDrawerState = true
                                }
                            }

                            CoroutineScope(Dispatchers.Main).launch {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(
                                        pin.position,
                                        when (zoomLevel) {
                                            ZOOM_LEVEL.CONTINENT -> ZOOM_LEVEL.COUNTRY
                                            ZOOM_LEVEL.COUNTRY -> ZOOM_LEVEL.CITY
                                            else -> zoomLevel
                                        }
                                    )
                                )
                            }
                            true // 클릭 이벤트 소비
                        }
                    ) {
                        CustomPin(
                            title = pin.title,
                            subtitle = pin.subtitle
                        )
                    }
                }
            }

            // 세션에 저장된 교통수단 핀 목록 (CITY 레벨에서만 표시)
            if (zoomLevel == ZOOM_LEVEL.CITY) {
                sessionTransportPin.forEach { transportPin ->
                    Polyline(
                        points = listOf(transportPin.from, transportPin.to),
                        zIndex = 2f,
                        color = when (transportPin.transportType) {
                            TransportType.WALKING -> CustomColors.Blue
                            TransportType.BICYCLE -> CustomColors.Green
                            TransportType.CAR -> CustomColors.Red
                            TransportType.BUS -> CustomColors.Yellow
                            TransportType.TRAIN -> CustomColors.Orange
                            TransportType.SUBWAY -> CustomColors.LightGray
                            TransportType.AIRPLANE -> CustomColors.Gray
                        },
                        width = 30f,
                        jointType = JointType.ROUND,
                        pattern = if (transportPin.transportType == model.TransportType.WALKING) {
                            listOf(
                                Dash(20f),
                                Gap(10f)
                            ) // 도보는 점선
                        } else {
                            null // 다른 교통수단은 실선
                        }
                    )

                    val polylineAngle = Math.toDegrees(
                        kotlin.math.atan2(
                            transportPin.to.longitude - transportPin.from.longitude,
                            transportPin.to.latitude - transportPin.from.latitude
                        )
                    )
                    val cameraAngle = cameraPositionState.position.bearing
                    MarkerComposable(
                        rotation = (polylineAngle - cameraAngle).toFloat(),
                        zIndex = 1f,
                        state = MarkerState(
                            position = LatLng(
                                (transportPin.from.latitude + transportPin.to.latitude) / 2,
                                (transportPin.from.longitude + transportPin.to.longitude) / 2
                            )
                        ),
                        onClick = {
                            focusManager.clearFocus() // 키보드 내리기
                            // 핀 클릭 시 카메라 위치 변경
                            CoroutineScope(Dispatchers.Main).launch {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(
                                        LatLng(
                                            (transportPin.from.latitude + transportPin.to.latitude) / 2,
                                            (transportPin.from.longitude + transportPin.to.longitude) / 2
                                        ),
                                        cameraPositionState.position.zoom
                                    )
                                )
                            }


                            true // 클릭 이벤트 소비
                        }
                    ) {
                        CustomTransportPin(
                            text = "${when (transportPin.transportType) {
                                TransportType.WALKING -> "도보"
                                TransportType.BICYCLE -> "자전거"
                                TransportType.CAR -> "자동차"
                                TransportType.BUS -> "버스"
                                TransportType.TRAIN -> "기차"
                                TransportType.SUBWAY -> "지하철"
                                TransportType.AIRPLANE -> "비행기"
                            }}로 이동",
                            borderColor = when (transportPin.transportType) {
                                TransportType.WALKING -> CustomColors.Blue
                                TransportType.BICYCLE -> CustomColors.Green
                                TransportType.CAR -> CustomColors.Red
                                TransportType.BUS -> CustomColors.Yellow
                                TransportType.TRAIN -> CustomColors.Orange
                                TransportType.SUBWAY -> CustomColors.LightGray
                                TransportType.AIRPLANE -> CustomColors.Gray
                            },
                        )
                    }
                }
            }

            // 유저가 선택한 핀 목록
            userSelectedMapPins.forEach { pin ->
                key("${pin.position.latitude},${pin.position.longitude},${pin.title},user") {
                    MarkerComposable(
                        state = MarkerState(position = pin.position),
                        zIndex = 3f,
                        onClick = {
                            focusManager.clearFocus() // 키보드 내리기
                            // 핀 클릭 시 카메라 위치 변경
                            CoroutineScope(Dispatchers.Main).launch {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(
                                        pin.position,
                                        cameraPositionState.position.zoom
                                    )
                                )
                            }
                            true // 클릭 이벤트 소비
                        }
                    ) {
                        CustomPin(
                            title = pin.title,
                            subtitle = pin.subtitle
                        )
                    }
                }
            }
        }

        // 검색 바와 검색 결과
        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                placeholder = "원하는 장소 또는 여행 검색",
                query = mapQuery,
                onQueryChange = {
                    mapQuery = it
                    searchJob?.cancel()
                    searchJob = CoroutineScope(Dispatchers.IO).launch {
                        delay(400)
                        if (mapQuery.isNotBlank()) {
                            try {
                                val results = PlaceUtil.searchPlaceByText(context, mapQuery)
                                searchResults = results
                            } catch (e: Exception) {
                                searchResults = emptyList()
                            }
                        } else {
                            searchResults = emptyList()
                        }
                    }
                },
                modifier = Modifier
                    .width(300.dp)
            )

            if (searchResults.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))

                Column(
                    modifier = Modifier
                        .width(300.dp)
                        .heightIn(max = 300.dp)
                        .shadow(
                            elevation = 1.dp,
                            shape = RoundedCornerShape(8.dp),
                            ambientColor = CustomColors.Black,
                            spotColor = CustomColors.Black
                        )
                        .background(
                            CustomColors.White
                        )
                ) {
                    Text(
                        text = "여행에서 검색 결과",
                        modifier = Modifier.padding(10.dp),
                        color = CustomColors.Black
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        item {
                            Text(
                                text = "여행 기록 ㅇㅇ",
                                modifier = Modifier
                                    .fillMaxWidth(),
                                color = CustomColors.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Divider(
                        color = CustomColors.LightGray,
                        thickness = 1.dp
                    )

                    Text(
                        text = "지도에서 검색 결과",
                        modifier = Modifier.padding(10.dp),
                        color = CustomColors.Black
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        itemsIndexed(searchResults) { index, prediction ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        CoroutineScope(Dispatchers.IO).launch {
                                            try {
                                                val placesClient = Places.createClient(context)
                                                val fields = listOf(
                                                    Place.Field.LAT_LNG,
                                                    Place.Field.NAME,
                                                    Place.Field.ADDRESS
                                                )
                                                val request = FetchPlaceRequest.builder(
                                                    prediction.placeId,
                                                    fields
                                                ).build()
                                                val response =
                                                    placesClient.fetchPlace(request).await()
                                                val latLng = response.place.latLng
                                                if (latLng != null) {
                                                    val newLatLng =
                                                        LatLng(latLng.latitude, latLng.longitude)

                                                    // 검색 결과로 핀 생성
                                                    val pinInfo = MapPin(
                                                        id = null,
                                                        type = MapPinType.SEARCH_RESULT,
                                                        position = newLatLng,
                                                        title = response.place.name
                                                            ?: prediction.getPrimaryText(null)
                                                                .toString(),
                                                        subtitle = response.place.address
                                                            ?: prediction.getSecondaryText(null)
                                                                .toString()
                                                    )

                                                    userSelectedMapPins = listOf(pinInfo)
                                                    cameraTarget = newLatLng
                                                }
                                            } catch (e: Exception) {
                                                Log.e("MapTab", "장소 이동 실패: $e")
                                            }
                                        }
                                        searchResults = emptyList()
                                    }
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = prediction.getFullText(null).toString(),
                                    color = CustomColors.Black
                                )
                            }
                            if (index != searchResults.lastIndex) {
                                Divider(
                                    color = CustomColors.LightGray,
                                    thickness = 1.dp
                                )
                            }
                        }
                    }
                }
            }
        }
        // 여행 정보 Bottom Drawer
        BottomDrawer (
            isOpen = tripInfoBottomDrawerState,
            onDismiss = { tripInfoBottomDrawerState = false }
        ) {
            val regions = MapTabData.getSessionRegions(context, selectedTripId as Int)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                for (region in regions) {
                    val subtitle = if (region.start_date != null && region.end_date != null) {
                        "${DatetimeUtil.dateToDotDate(region.start_date)} ~ ${
                            DatetimeUtil.dateToDotDate(
                                region.end_date
                            )
                        }"
                    } else {
                        null
                    }

                    RegionInfoButton(
                        title = region.title,
                        subtitle = subtitle,
                        onClick = {
                            focusManager.clearFocus() // 키보드 내리기
                            selectedRegionId = region.id
                            tripInfoBottomDrawerState = false
                            regionInfoBottomDrawerState = true

                            CoroutineScope(Dispatchers.Main).launch {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(
                                        LatLng(region.lat, region.lng),
                                        ZOOM_LEVEL.CITY
                                    )
                                )
                            }
                        }
                    )
                }
            }
        }

        // 지역 정보 Bottom Drawer
        BottomDrawer(
            isOpen = regionInfoBottomDrawerState,
            onDismiss = { regionInfoBottomDrawerState = false }
        ) {
            val regionId = selectedRegionId ?: return@BottomDrawer
            val schedules = MapTabData.getSessionSchedules(LocalContext.current, regionId)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                for((index, schedule) in schedules.withIndex()) {
                    val subtitle = if (schedule.start_datetime != null && schedule.end_datetime != null) {
                        "${DatetimeUtil.datetimeToTime(schedule.start_datetime)} ~ ${
                            DatetimeUtil.datetimeToTime(schedule.end_datetime)
                        }"
                    } else {
                        null
                    }

                    ScheduleInfoButton(
                        type = schedule.type,
                        title = schedule.title,
                        subtitle = subtitle,
                        onClick = {
                            focusManager.clearFocus() // 키보드 내리기
                            CoroutineScope(Dispatchers.Main).launch {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(
                                        LatLng(schedule.lat, schedule.lng),
                                        ZOOM_LEVEL.CITY
                                    )
                                )
                            }
                        }
                    )
                    if (index != schedules.lastIndex) {
                        val nextSchedule = schedules.getOrNull(index + 1) as model.Schedule
                        val transport = MapTabData.getSessionTransportByFromTo(
                            LocalContext.current,
                            regionId,
                            schedule.id,
                            nextSchedule.id
                        )
                        if (transport != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            TransportInfoButton(
                                type = transport.type,
                                subtitle = transport.duration,
                            )
                        } else {
                            TransportInfoButton(
                                type = null,
                            )
                        }
                    }
                }
            }
        }
    }

}


// suspend fun Task.await() 확장 함수 추가
suspend fun <T> Task<T>.await(): T = suspendCancellableCoroutine { cont ->
    addOnSuccessListener { cont.resume(it) }
    addOnFailureListener { cont.resumeWithException(it) }
}
