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
import androidx.compose.ui.unit.dp
import com.example.app.LocalSession
import com.example.app.type.SessionData
import com.example.app.ui.components.search_bar.SearchBar
import com.example.app.ui.components.map.MapPin
import com.example.app.ui.components.map.CustomPin
import com.example.app.ui.theme.CustomColors
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
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
import kotlin.text.compareTo

val INITIAL_LAT_LNG = LatLng(36.3730, 127.3622) // 지도의 초기 위치(카이스트)

object ZOOM_LEVEL {
    const val CONTINENT = 3f   // 대륙 수준
    const val COUNTRY = 6f     // 나라 수준
    const val CITY = 12f       // 도시 수준
}

// 저장된 핀 목록을 가져오기(여행, 지역, 일정)
fun getSessionPinsByZoomRate(
    sessionData: SessionData,
    zoomLevel: Float
): List<MapPin> {
    Log.d("MapTab", "getSessionPinsByZoomRate called with zoomLevel: $zoomLevel")
    val pins = when (zoomLevel) {
        ZOOM_LEVEL.CITY -> sessionData.trips.flatMap { trip ->
            trip.regions.flatMap { region ->
                region.schedules.map { schedule ->
                    MapPin(
                        position = LatLng(schedule.lat, schedule.lng),
                        title = schedule.title,
                        snippet = "Schedule"
                    )
                }
            }
        }
        ZOOM_LEVEL.COUNTRY -> sessionData.trips.flatMap { trip ->
            trip.regions.map { region ->
                MapPin(
                    position = LatLng(region.lat, region.lng),
                    title = region.title,
                    snippet = "Region"
                )
            }
        }
        else -> sessionData.trips.map { trip ->
            MapPin(
                position = LatLng(trip.lat, trip.lng),
                title = trip.title,
                snippet = "Trip"
            )
        }
    }

    return pins
}

@Composable
fun MapTab() {
    val context = LocalContext.current

    // 저장된 세션 데이터 불러오기
    val sessionData = LocalSession.current.value

    var mapQuery by remember { mutableStateOf("") }
    var searchJob: Job? = remember { null }
    var searchResults by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(INITIAL_LAT_LNG, ZOOM_LEVEL.CONTINENT)
    } // 카메라 위치 상태

    var cameraTarget by remember { mutableStateOf<LatLng?>(null) } // 카메라 이동용 상태

    var zoomLevel by remember { mutableStateOf(ZOOM_LEVEL.CONTINENT) } // 줌 레벨 상태

    var sessionMapPins by remember { mutableStateOf<List<MapPin>>(emptyList()) } // 세션에 저장된 핀 목록
    var userSelectedMapPins by remember { mutableStateOf<List<MapPin>>(emptyList()) } // 유저가 선택한 핀 목록

    // 카메라 이동은 여기서
    LaunchedEffect(cameraTarget) {
        cameraTarget?.let {
            val update = CameraUpdateFactory.newLatLngZoom(it, cameraPositionState.position.zoom)
            cameraPositionState.move(update)
        }
    }

    // 줌 레벨 변경 감지
    LaunchedEffect(zoomLevel) {
        Log.d("MapTab", "Zoom level changed to: ${
            when (zoomLevel) {
                ZOOM_LEVEL.CONTINENT -> "Continent"
                ZOOM_LEVEL.COUNTRY -> "Country"
                ZOOM_LEVEL.CITY -> "City"
                else -> "Unknown"
            }
        }")
        // 줌 레벨에 따라 세션에서 핀 목록 가져오기
        sessionMapPins = getSessionPinsByZoomRate(sessionData, zoomLevel)
        Log.d("MapTab", "Session map pins updated: ${sessionMapPins.size} pins")
    }

    // 줌 정도 실시간 감지
    LaunchedEffect(cameraPositionState) {
        snapshotFlow { cameraPositionState.position.zoom }
            .distinctUntilChanged()
            .collectLatest { zoomRate ->
                Log.d("MapTab", "Zoom rate changed: $zoomRate")

                zoomLevel = when {
                    zoomRate > ZOOM_LEVEL.CITY -> ZOOM_LEVEL.CITY
                    zoomRate > ZOOM_LEVEL.COUNTRY -> ZOOM_LEVEL.COUNTRY
                    else -> ZOOM_LEVEL.CONTINENT
                }

            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                compassEnabled = false  // 나침반 비활성화
            ),
            onMapClick = { latLng ->
                // 지도 클릭 시 기존 핀 대체
                CoroutineScope(Dispatchers.IO).launch {
                    val basicPinInfo = PlaceUtil.getLocationInfo(context, latLng)
                    val pinInfo = MapPin(
                        position = latLng,
                        title = basicPinInfo.title,
                        snippet = basicPinInfo.snippet
                    )
                    userSelectedMapPins = listOf(pinInfo)
                }
            }
        ) {
            // 세션에 저장된 핀 목록
            sessionMapPins.forEach { pin ->
                MarkerComposable(
                    state = MarkerState(position = pin.position)
                ) {
                    CustomPin(
                        content = {
                            Text(text = pin.title, color = CustomColors.Black)
                        }
                    )
                }
            }

            // 유저가 선택한 핀 목록
            userSelectedMapPins.forEach { pin ->
                MarkerComposable(
                    state = MarkerState(position = pin.position)
                ) {
                    CustomPin(
                        content = {
                        }
                    )
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
                    .width(358.dp)
            )

            if (searchResults.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))

                Column (
                    modifier = Modifier.width(300.dp)
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
                                                val fields = listOf(Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS)
                                                val request = FetchPlaceRequest.builder(prediction.placeId, fields).build()
                                                val response = placesClient.fetchPlace(request).await()
                                                val latLng = response.place.latLng
                                                if (latLng != null) {
                                                    val newLatLng = LatLng(latLng.latitude, latLng.longitude)

                                                    // 검색 결과로 핀 생성
                                                    val pinInfo = MapPin(
                                                        position = newLatLng,
                                                        title = response.place.name ?: prediction.getPrimaryText(null).toString(),
                                                        snippet = response.place.address ?: prediction.getSecondaryText(null).toString()
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

    }
}


// suspend fun Task.await() 확장 함수 추가
suspend fun <T> Task<T>.await(): T = suspendCancellableCoroutine { cont ->
    addOnSuccessListener { cont.resume(it) }
    addOnFailureListener { cont.resumeWithException(it) }
}
