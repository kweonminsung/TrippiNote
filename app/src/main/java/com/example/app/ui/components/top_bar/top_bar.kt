package com.example.app.ui.components.top_bar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.app.TabType


@Composable
fun Header(
    tabType: TabType,
    modifier: Modifier = Modifier
) {
    when (tabType) {
        TabType.HOME -> {
            HomeTopBar()
        }
        TabType.ALBUM -> {
            AlbumTopBar()
        }
        TabType.MAP -> {
            MapTopBar()
        }
        TabType.PROFILE -> {
            ProfileTopBar()
        }
    }
}

val onSettingsClick: () -> Unit = {
    println("Settings clicked")
}
