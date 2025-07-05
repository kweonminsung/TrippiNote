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
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

val INITIAL_LAT_LNG = LatLng(36.3730, 127.3622) // 지도의 초기 위치(카이스트)

// 위치 정보를 가져오는 함수 (Geocoding API 사용)
suspend fun getLocationInfo(context: android.content.Context, latLng: LatLng): MapPin {
    return try {
        val placesClient = Places.createClient(context)

        // Geocoding API를 통해 역방향 지오코딩 수행
        val geocoder = android.location.Geocoder(context, java.util.Locale.getDefault())
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        val title = if (addresses?.isNotEmpty() == true) {
            val address = addresses[0]
            address.featureName ?: address.thoroughfare ?: address.locality ?: "선택한 위치"
        } else {
            "선택한 위치"
        }

        val snippet = if (addresses?.isNotEmpty() == true) {
            val address = addresses[0]
            address.getAddressLine(0) ?: "주소 정보 없음"
        } else {
            "위도: ${String.format("%.4f", latLng.latitude)}, 경도: ${String.format("%.4f", latLng.longitude)}"
        }

        MapPin(latLng, title, snippet)
    } catch (e: Exception) {
        Log.e("MapTab", "위치 정보 가져오기 ���패: $e")
        MapPin(
            latLng,
            "선택한 위치",
            "위도: ${String.format("%.4f", latLng.latitude)}, 경도: ${String.format("%.4f", latLng.longitude)}"
        )
    }
}

@Composable
fun MapTab() {
    val context = LocalContext.current

    var mapQuery by remember { mutableStateOf("") }
    var searchJob: Job? = remember { null }
    var searchResults by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(INITIAL_LAT_LNG, 10f)
    }

    var selectedLatLng by remember { mutableStateOf<LatLng?>(null) }
    var cameraTarget by remember { mutableStateOf<LatLng?>(null) } // 카메라 이동용 상태
    var mapPins by remember { mutableStateOf<List<MapPin>>(emptyList()) } // 핀 목록
    var showPinInfo by remember { mutableStateOf(false) } // 핀 정보 카드 표시 상태

    // 카메라 이동은 여기서
    LaunchedEffect(cameraTarget) {
        cameraTarget?.let {
            val update = CameraUpdateFactory.newLatLngZoom(it, 15f)
            cameraPositionState.move(update)
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
                    val basicPinInfo = getLocationInfo(context, latLng)
                    val pinInfo = MapPin(
                        position = latLng,
                        title = basicPinInfo.title,
                        snippet = basicPinInfo.snippet
                    )
                    mapPins = listOf(pinInfo)
                    selectedLatLng = latLng
                    showPinInfo = true
                }
            }
        ) {
            // 커스텀 Compose 핀 표시
            mapPins.forEach { pin ->
                MarkerComposable(
                    state = MarkerState(position = pin.position)
                ) {
                    CustomPin(
                        content = {}
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

                                                    mapPins = listOf(pinInfo)
                                                    selectedLatLng = newLatLng
                                                    cameraTarget = newLatLng
                                                    showPinInfo = true
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
