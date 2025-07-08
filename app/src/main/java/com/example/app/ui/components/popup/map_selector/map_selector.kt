package com.example.app.ui.components.popup.map_selector

import PlaceUtil
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.app.ui.components.map.CustomPin
import com.example.app.ui.components.map.MapPin
import com.example.app.ui.components.map.MapPinType
import com.example.app.ui.pages.map.INITIAL_LAT_LNG
import com.example.app.ui.pages.map.INITIAL_ZOOM_LEVEL
import com.example.app.ui.pages.map.MapSearchResult
import com.example.app.ui.pages.map.await
import com.example.app.ui.theme.CustomColors
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
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
import kotlinx.coroutines.launch

@Composable
fun MapSelector(
    onDismiss: () -> Unit = {},
    setIsLocSelecting: (Boolean) -> Unit,
    locValue: PlaceUtil.LocationInfo? = null,
    setLocValue: (PlaceUtil.LocationInfo?) -> Unit,
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var mapQuery by remember { mutableStateOf("") }
    var searchJob: Job? = remember { null }
    var searchResults by remember { mutableStateOf<List<MapSearchResult>>(emptyList()) }

    // 카메라 위치 상태
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            locValue?.position ?: INITIAL_LAT_LNG
            , INITIAL_ZOOM_LEVEL)
    }
    var cameraTarget by remember { mutableStateOf<LatLng?>(null) } // 카메라 이동용 상태

    var userSelectedMapPin by remember { mutableStateOf<MapPin?>(
        locValue?.let {
            MapPin(
                id = null,
                type = MapPinType.USER_SELECTED,
                position = it.position,
                title = it.title,
                subtitle = it.snippet
            )
        }
    ) } // 유저가 선택한 핀

    // 카메라 이동은 여기서
    LaunchedEffect(cameraTarget) {
        cameraTarget?.let {
            val update = CameraUpdateFactory.newLatLngZoom(it, cameraPositionState.position.zoom)
            cameraPositionState.animate(update)
        }
    }

    BackHandler(onBack = onDismiss) // 뒤로가기 버튼 핸들러
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box() {
            Column(modifier = Modifier.fillMaxSize()) {
                GoogleMap(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    cameraPositionState = cameraPositionState,
                    uiSettings = MapUiSettings(
                        compassEnabled = false,  // 나침반 비활성화
                        myLocationButtonEnabled = false, // 내 위치 버튼 비활성화
                        mapToolbarEnabled = false, // 지도 툴바 비활성화
                    ),
                    onMapClick = { latLng ->
                        focusManager.clearFocus() // 키보드 내리기
                        // 핀이 있으면 삭제, 없으면 새로 추가
                        if (userSelectedMapPin != null) {
                            userSelectedMapPin = null
                        } else {
                            CoroutineScope(Dispatchers.IO).launch {
                                val locationInfo = PlaceUtil.getLocationInfo(context, latLng)
                                userSelectedMapPin = MapPin(
                                    id = null,
                                    type = MapPinType.USER_SELECTED,
                                    position = latLng,
                                    title = locationInfo.title,
                                    subtitle = locationInfo.snippet
                                )
                                // 핀 생성 시 카메라 위치 변경
                                CoroutineScope(Dispatchers.Main).launch {
                                    cameraPositionState.animate(
                                        CameraUpdateFactory.newLatLngZoom(
                                            latLng,
                                            cameraPositionState.position.zoom
                                        )
                                    )
                                }
                            }
                        }
                    }
                ) {
                    // 유저가 선택한 핀
                    userSelectedMapPin?.let { pin ->
                        key("${pin.position.latitude},${pin.position.longitude},${pin.title}") {
                            MarkerComposable(
                                state = MarkerState(position = pin.position),
                                zIndex = 2f,
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(CustomColors.White),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "지도에서 위치를 선택하세요",
                        fontSize = 16.sp,
                        color = CustomColors.DarkGray,
                        modifier = Modifier.padding(16.dp)
                    )
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                focusManager.clearFocus() // 키보드 내리기
                                setLocValue(null)
                                setIsLocSelecting(false) // 위치 선택 취소
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "취소",
                            fontSize = 16.sp,
                            color = CustomColors.Blue,
                        )
                    }
                }
            }

            userSelectedMapPin?.let {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 120.dp)
                            .background(
                                color = CustomColors.TranslucentWhite,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .width(160.dp)
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(
                                        elevation = 1.dp,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .background(
                                        color = CustomColors.White,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        focusManager.clearFocus() // 키보드 내리기
                                        setLocValue(
                                            PlaceUtil.LocationInfo(
                                                title = it.title,
                                                snippet = it.subtitle ?: "",
                                                position = it.position
                                            )
                                        ) // 선택한 위치 설정
                                        Log.d(
                                            "MapSelector",
                                            "Selected location: ${it.position.latitude}, ${it.position.longitude}"
                                        )
                                        setIsLocSelecting(false) // 위치 선택 완료
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "선택 완료",
                                    modifier = Modifier
                                        .padding(vertical = 14.dp),
                                    fontSize = 16.sp,
                                    color = CustomColors.Black,
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(
                                        elevation = 1.dp,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .background(
                                        color = CustomColors.Red,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        focusManager.clearFocus() // 키보드 내리기
                                        userSelectedMapPin = null // 핀 삭제
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "핀 삭제",
                                    modifier = Modifier
                                        .padding(vertical = 14.dp),
                                    fontSize = 16.sp,
                                    color = CustomColors.White,
                                )
                            }
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
                MapSingleSearchBar(
                    query = mapQuery,
                    onQueryChange = { newQuery, jobSetter ->
                        mapQuery = newQuery
                        searchJob?.cancel()
                        searchJob = jobSetter
                    },
                    onSearchResults = { results ->
                        searchResults = results
                    }
                )
                MapSingleSearchResults(
                    searchResults = searchResults,
                    onResultClick = { searchResult ->
                        focusManager.clearFocus() // 키보드 내리기

                        CoroutineScope(Dispatchers.IO).launch {
                            val placesClient = PlaceUtil.getPlacesClient(context)

                            val request = FetchPlaceRequest.builder(
                                searchResult.placeId as String,
                                listOf(
                                    Place.Field.LAT_LNG,
                                    Place.Field.NAME,
                                    Place.Field.ADDRESS
                                )
                            ).build()
                            val response = placesClient.fetchPlace(request).await()
                            val latLng = response.place.latLng

                            if (latLng != null) {
                                val newLatLng = LatLng(latLng.latitude, latLng.longitude)

                                userSelectedMapPin = MapPin(
                                    id = null,
                                    type = MapPinType.SEARCH_RESULT,
                                    position = newLatLng,
                                    title = response.place.name
                                        ?: searchResult.title,
                                    subtitle = response.place.address
                                        ?: searchResult.subtitle
                                )
                                cameraTarget = newLatLng
                            } else {
                                Log.e("MapTab", "LatLng is null for place: ${searchResult.title}")
                            }
                        }
                        searchResults = emptyList()
                        mapQuery = "" // 검색어 초기화
                    }
                )
            }
        }
    }
}