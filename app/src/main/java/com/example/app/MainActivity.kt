package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.app.ui.theme.AppTheme
import com.example.app.ui.components.Header

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
    var tabType: TabType = TabType.HOME

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Header(tabType, )

    }
}
