package com.example.app.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.app.ui.theme.CustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    title: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CustomColors.White,
            titleContentColor = CustomColors.Black,
        )
    )
}