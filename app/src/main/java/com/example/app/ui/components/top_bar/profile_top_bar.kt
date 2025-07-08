package com.example.app.ui.components.top_bar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.CustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar() {
    TopAppBar(
        title = {
            Row (

            ){
                Text(
                    text = "프로필",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 12.dp),
                )
//                Icon(
//                    imageVector = Icons.Default.Settings,
//                    contentDescription = null,
//                    modifier = Modifier.padding(end = 8.dp)
//                )
            }
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