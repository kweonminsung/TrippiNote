package com.example.app.ui.components.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng

// 핀 정보를 담는 데이터 클래스
data class MapPin(
    val position: LatLng,
    val title: String,
    val snippet: String,
)

// 커스텀 핀 모양 (직사각형 + 아래 삼각형)
class PinShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val width = size.width
            val height = size.height
            val cornerRadius = 12f
            val triangleHeight = 8f
            val triangleWidth = 16f
            val bodyHeight = height - triangleHeight

            // 직사각형 몸체 (둥근 모서리)
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        left = 0f,
                        top = 0f,
                        right = width,
                        bottom = bodyHeight
                    ),
                    radiusX = cornerRadius,
                    radiusY = cornerRadius
                )
            )

            // 아래쪽 삼각형 (말꼬리)
            val triangleStartX = (width - triangleWidth) / 2f
            val triangleEndX = triangleStartX + triangleWidth
            val triangleCenterX = width / 2f

            moveTo(triangleStartX, bodyHeight)
            lineTo(triangleCenterX, height)
            lineTo(triangleEndX, bodyHeight)
            close()
        }
        return Outline.Generic(path)
    }
}

// 커스텀 핀 Compose 컴포넌트
@Composable
fun CustomPin(
    title: String,
    isSearchResult: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clickable { onClick() }
    ) {
        // 핀 몸체
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .widthIn(min = 80.dp, max = 150.dp)
                .clip(PinShape())
                .background(
                    color = Color(0xFF2196F3)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                // 아이콘 영역
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "📍",
                        fontSize = 10.sp
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                // 제목 텍스트
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }
        }
    }
}
