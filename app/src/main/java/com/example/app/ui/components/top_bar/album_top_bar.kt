package com.example.app.ui.components.top_bar

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.CustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumTopBar(
    username: String
) {
    TopAppBar(
        title = {
            Text(
                text = "앨범",
                fontSize = 21.sp
            )
        },
        actions = {
            Button(onClick = { /* TODO: 앨범 추가 */ }) {
                Text("추가")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CustomColors.White,
            titleContentColor = CustomColors.Black,
            actionIconContentColor = CustomColors.Black
        ),
    )
}