package com.example.app.ui.components.map

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.CustomColors
import com.example.app.util.DatetimeUtil
import com.example.app.util.database.model

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RawTransportButton(
    type: model.TransportType? = null,
    duration: String? = null,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .background(
                color = CustomColors.White
            )
            .border(
                width = 2.dp,
                color = type?.toColor() ?: CustomColors.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            )
        ,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = type?.toStringKor() ?: "교통수단 추가",
                color = CustomColors.Black,
                fontSize = 12.sp
            )

            if(duration != null && duration.isNotEmpty()) {
                Text(
                    text = DatetimeUtil.timeToHourMinute(duration),
                    color = CustomColors.DarkGray,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun TransportInfoButton(
    type: model.TransportType? = null,
    duration: String? = null,
    saveFn: (type: model.TransportType, duration: String?) -> Unit,
) {
    val (isTransportSetting, setIsTransportSetting) = remember { mutableStateOf(false) } // 교통수단 설정 중 여부
    val (isTransportDurationSetting, setIsTransportDurationSetting) = remember { mutableStateOf(false) } // 교통수단 소요시간 설정 중 여부

    Box(
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(Color.Gray, CircleShape)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            if(isTransportSetting) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    RawTransportButton(
                        type = model.TransportType.WALKING,
                        onClick = {
                            setIsTransportSetting(false)
                            saveFn(model.TransportType.WALKING, duration)
                        }
                    )
                    RawTransportButton(
                        type = model.TransportType.BICYCLE,
                        onClick = {
                            setIsTransportSetting(false)
                            saveFn(model.TransportType.BICYCLE, duration)
                        }
                    )
                    RawTransportButton(
                        type = model.TransportType.CAR,
                        onClick = {
                            setIsTransportSetting(false)
                            saveFn(model.TransportType.CAR, duration)
                        }
                    )
                    RawTransportButton(
                        type = model.TransportType.TAXI,
                        onClick = {
                            setIsTransportSetting(false)
                            saveFn(model.TransportType.TAXI, duration)
                        }
                    )
                    RawTransportButton(
                        type = model.TransportType.BUS,
                        onClick = {
                            setIsTransportSetting(false)
                            saveFn(model.TransportType.BUS, duration)
                        }
                    )
                    RawTransportButton(
                        type = model.TransportType.TRAIN,
                        onClick = {
                            setIsTransportSetting(false)
                            saveFn(model.TransportType.TRAIN, duration)
                        }
                    )
                    RawTransportButton(
                        type = model.TransportType.SUBWAY,
                        onClick = {
                            setIsTransportSetting(false)
                            saveFn(model.TransportType.SUBWAY, duration)
                        }
                    )
                    RawTransportButton(
                        type = model.TransportType.AIRPLANE,
                        onClick = {
                            setIsTransportSetting(false)
                            saveFn(model.TransportType.AIRPLANE, duration)
                        }
                    )
                }
            } else {
                RawTransportButton(
                    type = type,
                    duration = duration,
                    onClick = {
                        setIsTransportSetting(true) // 교통 수단 설정 시작
                    },
                    onLongClick = {
                        setIsTransportDurationSetting(true) // 교통 수단 소요시 간 설정 시작
                    }
                )
                if(type != null && isTransportDurationSetting) {
                    TransportTimeForm(
                        duration = duration,
                        setIsTransportDurationSetting = setIsTransportDurationSetting,
                        saveTimeFn = { hour, minute ->
                            saveFn(
                                type,
                                DatetimeUtil.hourMinuteToTime(hour, minute)
                            )
                        },
                    )
                }
            }
        }
    }
}