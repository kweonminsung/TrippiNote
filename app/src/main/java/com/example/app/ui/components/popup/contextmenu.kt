package com.example.app.ui.components.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.app.ui.theme.CustomColors

@Composable
fun ContextMenu(
    expanded: Boolean,
    topLabel: String,
    bottomLabel: String,
    onClickTop: () -> Unit,
    onClickBottom: () -> Unit,
    onDismiss: () -> Unit = { }
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        modifier = Modifier
            .width(60.dp)
            .background(
                color = CustomColors.White,
                shape = RoundedCornerShape(8.dp)
            )
            .background(CustomColors.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickTop() }
                .height(32.dp)
                .background(
                    shape = RectangleShape,
                    color = CustomColors.White
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = topLabel,
                color = CustomColors.Black,
            )
        }

        HorizontalDivider(
            color = CustomColors.LightGray,
            thickness = 1.dp
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickBottom() }
                .height(32.dp)
                .background(
                    shape = RectangleShape,
                    color = CustomColors.White
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = bottomLabel,
                color = CustomColors.Black,
            )
        }
    }
}