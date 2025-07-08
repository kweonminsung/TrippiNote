package com.example.app.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app.ui.theme.CustomColors
import com.example.app.util.database.model

@Composable
fun ScheduleTypeButton(
    type: model.ScheduleType,
    selectedType: model.ScheduleType = type,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .width(60.dp)
            .height(30.dp)
            .border(
                width = 3.dp,
                color = when (type) {
                    model.ScheduleType.SIGHTS -> CustomColors.Blue
                    model.ScheduleType.HOTEL -> CustomColors.DarkGray
                    model.ScheduleType.RESTAURANT -> CustomColors.Orange
                    model.ScheduleType.ETC -> CustomColors.Red
                },
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = if(type == selectedType) {
                    when (type) {
                        model.ScheduleType.SIGHTS -> CustomColors.Blue
                        model.ScheduleType.HOTEL -> CustomColors.DarkGray
                        model.ScheduleType.RESTAURANT -> CustomColors.Orange
                        model.ScheduleType.ETC -> CustomColors.Red
                    }
                } else {
                    CustomColors.White
                },
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when (type) {
                model.ScheduleType.SIGHTS -> "관광"
                model.ScheduleType.HOTEL -> "숙소"
                model.ScheduleType.RESTAURANT -> "음식점"
                model.ScheduleType.ETC -> "기타"
            },
            color = if (type == selectedType) {
                CustomColors.White
            } else {
                CustomColors.Black
            }
        )
    }
}