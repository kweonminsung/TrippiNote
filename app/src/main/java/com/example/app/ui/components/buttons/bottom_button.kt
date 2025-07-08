package com.example.app.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.app.ui.theme.CustomColors

@Composable
fun BottomButton(
    label: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(8.dp),
            )
            .background(
                color = CustomColors.DarkGray,
                shape = RoundedCornerShape(8.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = CustomColors.DarkGray, // 버튼 배경색
            contentColor = Color.White // 텍스트 색
        ),
    ) {
        Text(
            text = label,
            color = Color.White
        )
    }
}