package com.example.app.ui.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
            .width(203.dp)
            .shadow(
                elevation = 1.dp,
                shape = RectangleShape,
                ambientColor = CustomColors.Black,
                spotColor = CustomColors.Black,
            )
        ,
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