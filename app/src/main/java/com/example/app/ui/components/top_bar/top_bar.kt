package com.example.app.ui.components.top_bar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.app.LocalSession
import com.example.app.TabType
import kotlin.text.get


@Composable
fun TopBar(
    tabType: TabType,
) {
    val sessionData = LocalSession.current.value
    val username = sessionData.user.name

    when (tabType) {
        TabType.HOME -> {
            HomeTopBar(username)
        }
        TabType.ALBUM -> {
            AlbumTopBar(username)
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

