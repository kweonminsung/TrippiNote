package com.example.app.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.app.ui.theme.CustomColors
import okhttp3.internal.concurrent.TaskRunner

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = Modifier
            .padding(8.dp),
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Close",
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(CustomColors.DarkGray)
                .padding(4.dp),
            tint = CustomColors.White
        )
    }
}