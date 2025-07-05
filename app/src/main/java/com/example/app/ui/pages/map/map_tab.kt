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
import com.google.maps.android.compose.Marker
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

@Composable
fun MapTab() {
    var mapQuery by remember { mutableStateOf("") }
    val context = LocalContext.current
    var searchJob: Job? = remember { null }
    var searchResults by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(INITIAL_LAT_LNG, 10f)
    }

    var selectedLatLng by remember { mutableStateOf<LatLng?>(null) }
    var cameraTarget by remember { mutableStateOf<LatLng?>(null) } // 👈 카메라 이동용 상태

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
            )
        ) {
            Marker(
                state = MarkerState(position = selectedLatLng ?: INITIAL_LAT_LNG),
                title = if (selectedLatLng != null) "검색 위치" else "알 수 없는 위치",
                snippet = if (selectedLatLng != null) "검색한 위치입니다" else "오류"
            )
        }

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
//                                Log.d("MapTab", "검색 결과: $results")
                            } catch (e: Exception) {
                                searchResults = emptyList()
//                                Log.e("MapTab", "검색 실패: $e")
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
                                                val fields = listOf(Place.Field.LAT_LNG)
                                                val request = FetchPlaceRequest.builder(prediction.placeId, fields).build()
                                                val response = placesClient.fetchPlace(request).await()
                                                val latLng = response.place.latLng
                                                if (latLng != null) {
                                                    val newLatLng = LatLng(latLng.latitude, latLng.longitude)
                                                    selectedLatLng = newLatLng
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
