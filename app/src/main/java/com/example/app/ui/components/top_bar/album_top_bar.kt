package com.example.app.ui.components.top_bar

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumTopBar(
    username: String
) {
    TopAppBar(
        title = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = com.example.app.ui.theme.CustomColors.LighterGray,
            titleContentColor = com.example.app.ui.theme.CustomColors.Black
        )
    )
}


