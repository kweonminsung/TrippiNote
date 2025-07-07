package com.example.app.ui.components.map

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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

@Composable
fun TransportInfoButton(
    onClick: () -> Unit = {},
    type: model.TransportType? = null,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable(onClick = onClick)
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

            Box(
                modifier = Modifier
                    .background(
                        color = CustomColors.White
                    )
                    .border(
                        width = 2.dp,
                        color = when(type) {
                            model.TransportType.WALKING -> CustomColors.Blue
                            model.TransportType.BICYCLE -> CustomColors.Green
                            model.TransportType.CAR -> CustomColors.Red
                            model.TransportType.BUS -> CustomColors.Yellow
                            model.TransportType.TRAIN -> CustomColors.Orange
                            model.TransportType.SUBWAY -> CustomColors.LightGray
                            model.TransportType.AIRPLANE -> CustomColors.Gray
                            null -> CustomColors.Gray
                        },
                        shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .clickable(onClick = onClick),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = when (type) {
                                model.TransportType.WALKING -> "도보"
                                model.TransportType.BICYCLE -> "자전거"
                                model.TransportType.CAR -> "자동차"
                                model.TransportType.BUS -> "버스"
                                model.TransportType.TRAIN -> "기차"
                                model.TransportType.SUBWAY -> "지하철"
                                model.TransportType.AIRPLANE -> "비행기"
                                null -> "교통수단 추가"
                            },
                        color = CustomColors.Black,
                        fontSize = 12.sp
                    )

                    if(subtitle != null && subtitle.isNotEmpty()) {
                        Text(
                            text = DatetimeUtil.timeToHourMinute(subtitle),
                            color = CustomColors.DarkGray,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }
}