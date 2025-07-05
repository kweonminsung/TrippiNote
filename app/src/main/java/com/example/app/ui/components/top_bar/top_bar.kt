package com.example.app.ui.components.top_bar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.app.LocalSession
import com.example.app.TabType
import kotlin.text.get


@Composable
fun TopBar(
    tabType: TabType,
    modifier: Modifier = Modifier
) {
    val sessionData = LocalSession.current.value
    val userMap = sessionData.data["user"] as? Map<*, *>
    val username = userMap?.get("name") as? String ?: "게스트"

    when (tabType) {
        TabType.HOME -> {
            HomeTopBar(username)
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

