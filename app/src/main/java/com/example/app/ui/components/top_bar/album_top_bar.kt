package com.example.app.ui.components.top_bar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.CustomColors
//import com.example.app.ui.pages.album.createAlbumPath
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.app.R.drawable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumTopBar(
    username: String
) {
    TopAppBar(
        title = {
            Text(
                text = "${username}의 앨범",
                fontSize = 21.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
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


