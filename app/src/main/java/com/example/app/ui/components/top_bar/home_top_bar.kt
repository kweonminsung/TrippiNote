package com.example.app.ui.components.top_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.CustomColors
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    username: String,
) {
    val context = LocalContext.current

    TopAppBar(
        title = {
            Text(
                text = "${username}의 여행 다이어리  \uD83D\uDCDA",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp),
                fontSize = 20.sp
            )
        },
        actions = {

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CustomColors.LighterGray,
            titleContentColor = CustomColors.Black,
            actionIconContentColor = CustomColors.Black
        ),
    )
}