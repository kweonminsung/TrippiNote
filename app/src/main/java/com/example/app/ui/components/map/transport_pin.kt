package com.example.app.ui.components.map

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.type.TransportType
import com.google.android.gms.maps.model.LatLng

data class TransportPin(
    val from: LatLng,
    val to: LatLng,
    val text: String,
    val transportType: TransportType
)

@Composable
fun CustomTransportPin(
    modifier: Modifier = Modifier,
    text: String,
    textBgColor: Color = Color.White,
    textColor: Color = Color.Black,
    borderColor: Color = Color(0xFF2196F3),
    borderWidth: Dp = 2.dp,
    cornerRadius: Dp = 16.dp
) {
    Box(
        modifier = modifier
            .background(
                color = textBgColor,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(cornerRadius)
            )
            .border(
                width = borderWidth,
                color = borderColor,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(cornerRadius)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 16.sp
        )
    }
}
