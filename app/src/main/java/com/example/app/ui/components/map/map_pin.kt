package com.example.app.ui.components.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.app.ui.theme.CustomColors
import com.google.android.gms.maps.model.LatLng

// 핀 정보를 담는 데이터 클래스
data class MapPin(
    val position: LatLng,
    val title: String,
    val subTitle: String? = null,
)

@Composable
fun CustomPin(
    title: String,
    subTitle: String? = null,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box (
        modifier = modifier
            .padding(4.dp)
            .widthIn(
                min = 40.dp,
                max = 120.dp
            )
    ) {
        // 네 방향 그림자 효과
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 1.dp, y = 1.dp)
                .background(
                    color = CustomColors.Black.copy(alpha = 0.18f),
                    shape = RoundedCornerShape(8.dp)
                )
        )

        Column(
            modifier = Modifier
                .background(
                    color = CustomColors.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .clickable { onClick() }
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = CustomColors.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CustomColors.White),
                textAlign = TextAlign.Center,
                maxLines = 1,
                softWrap = true,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )

            if (subTitle != null && subTitle.isNotEmpty()) {
                Text(
                    text = subTitle,
                    color = CustomColors.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
