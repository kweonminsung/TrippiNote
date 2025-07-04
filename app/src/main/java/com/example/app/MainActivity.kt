package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.app.ui.theme.AppTheme
import com.example.app.ui.pages.AlbumTab
import com.example.app.ui.pages.HomeTab
import com.example.app.ui.pages.MapTab
import com.example.app.ui.pages.ProfileTab
import com.example.app.ui.components.BottomBar
import com.example.app.ui.components.top_bar.TopBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }
}

enum class TabType {
    HOME, ALBUM, MAP, PROFILE
}


@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val (tabType, setTabType) = remember { mutableStateOf(TabType.HOME) }

    Scaffold(
        topBar = {
            TopBar(tabType)
        },
        bottomBar = {
            BottomBar(setTabType)
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (tabType) {
                TabType.HOME -> HomeTab()
                TabType.ALBUM -> AlbumTab()
                TabType.MAP -> MapTab()
                TabType.PROFILE -> ProfileTab()
            }
        }
    }
}
