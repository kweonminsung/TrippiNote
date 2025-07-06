package com.example.app.ui.components.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.app.ui.theme.CustomColors
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
            val cornerRadius = 8f
            val triangleHeight = 50f
            val triangleWidth = 50f
            val bodyHeight = height - triangleHeight

            // 직사각형 몸체
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
    content: @Composable () -> Unit,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(PinShape())
            .size(
                width = 100.dp,
                height = 80.dp
            )
            .clickable { onClick() }
    ) {
        // 핀 몸체
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .widthIn(min = 100.dp, max = 150.dp)
                .background(
                    color = CustomColors.White,
                )
                .padding(8.dp)
        ) {
            content()
        }
    }
}
