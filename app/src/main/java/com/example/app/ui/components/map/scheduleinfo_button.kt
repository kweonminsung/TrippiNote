package com.example.app.ui.components.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.CustomColors
import com.example.app.util.database.model
import com.example.app.R

@Composable
fun ScheduleInfoButton(
    onClick: () -> Unit = {},
    type: model.ScheduleType,
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(8.dp),
                ambientColor = CustomColors.Black,
                spotColor = CustomColors.Black
            )
            .fillMaxWidth()
            .height(64.dp)
            .background(color = CustomColors.White, shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row() {
                Image(
                    painter = painterResource(id = when (type) {
                        model.ScheduleType.SIGHTS -> R.drawable.map_sights
                        model.ScheduleType.HOTEL -> R.drawable.map_hotel
                        model.ScheduleType.RESTAURANT -> R.drawable.map_restaurant
                        model.ScheduleType.ETC -> R.drawable.map_etc
                    }),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = when (type) {
                            model.ScheduleType.SIGHTS -> "관광"
                            model.ScheduleType.HOTEL -> "숙소"
                            model.ScheduleType.RESTAURANT -> "음식점"
                            model.ScheduleType.ETC -> "기타"
                        },
                        color = CustomColors.Gray,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = title,
                        color = CustomColors.Black,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = if(subtitle != null && subtitle.isNotEmpty()) Modifier.width(155.dp) else Modifier
                    )
                }
            }

            if(subtitle != null && subtitle.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .background(
                            color = CustomColors.LightGray,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .width(80.dp)
                        .height(24.dp)
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = subtitle,
                        color = CustomColors.DarkGray,
                        fontSize = 9.sp
                    )
                }
            }
        }
    }
}