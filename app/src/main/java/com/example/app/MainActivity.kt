package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.*
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import com.example.app.ui.theme.AppTheme
import com.example.app.ui.components.top_bar.Header
import com.example.app.ui.pages.AlbumTab
import com.example.app.ui.pages.HomeTab
import com.example.app.ui.pages.MapTab
import com.example.app.ui.pages.ProfileTab
import com.example.app.ui.components.BottomBar
import tabType

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
    var tabType by remember { mutableStateOf(TabType.HOME) }

    Scaffold(
        topBar = {
//            TopAppBar(
//                title = { Text("상단바 제목") }
//            )
        },
        bottomBar = { BottomBar { selectedTab ->
            tabType = selectedTab } }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row {
                Button(onClick = { tabType = TabType.HOME }) { Text("홈") }
                Button(onClick = { tabType = TabType.ALBUM }) { Text("앨범") }
                Button(onClick = { tabType = TabType.MAP }) { Text("지도") }
                Button(onClick = { tabType = TabType.PROFILE }) { Text("프로필") }
            }
            Header(tabType)

            when (tabType) {
                TabType.HOME -> HomeTab()
                TabType.ALBUM -> AlbumTab()
                TabType.MAP -> MapTab()
                TabType.PROFILE -> ProfileTab()
            }
          }
    }
}
