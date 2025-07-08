package com.example.app.ui.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.app.ui.theme.CustomColors
import com.example.app.util.database.model

@Composable
fun TransportTypeButton(
    type: model.TransportType,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .background(
                color = if (selected) type.toColor() else CustomColors.White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 2.dp,
                color = type.toColor(),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            ) {
            Image(
                painter = painterResource(id = if(selected) type.toEmptyIcon() else type.toIcon()),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onClick)
            )
            Text(
                text = type.toString(),
                color = if (selected) CustomColors.White else CustomColors.Black,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable(onClick = onClick)
            )
        }
    }
}