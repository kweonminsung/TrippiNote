package com.example.app

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.app.type.EXAMPLE_SESSION_DATA
import com.example.app.type.SessionData
import com.example.app.ui.theme.AppTheme
import com.example.app.ui.pages.album.AlbumTab
import com.example.app.ui.pages.HomeTab
import com.example.app.ui.pages.map.MapTab
import com.example.app.ui.pages.profile.ProfileTab
import com.example.app.ui.components.BottomBar
import com.example.app.ui.components.top_bar.TopBar
import com.example.app.util.KeyValueStore
import com.example.app.util.database.DatabaseUtil
import com.google.android.libraries.places.api.Places

val LocalSession = compositionLocalOf<MutableState<SessionData>> { error("No Storage provided") }

class MainActivity : ComponentActivity() {
    private fun getMetaDataValue(key: String): String? {
        return try {
            val appInfo = packageManager
                .getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            appInfo.metaData?.getString(key)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Places API 초기화
        val apiKey = getMetaDataValue("com.google.android.geo.API_KEY")
        if (!Places.isInitialized() && apiKey != null) {
            Places.initialize(applicationContext, apiKey)
        }

        // Database 초기화
        DatabaseUtil.dropAllTables(this)
        DatabaseUtil.createAllTables(this)
        DatabaseUtil.insertMockData(this)

        setContent {
            val context = this

            KeyValueStore.clear(context)

            // 예시 데이터 삽입
            KeyValueStore.saveBulk(context, EXAMPLE_SESSION_DATA)



            // KeyValueStore에서 가져온 데이터를 LocalSession에 저장
            val initialSessionData = KeyValueStore.loadBulk(context) as SessionData

            val session = remember { mutableStateOf(initialSessionData) }

            Log.d("SessionData", "Initial Session Data: ${session.value}")

            CompositionLocalProvider(LocalSession provides session) {
                AppTheme {
                    MainScreen()
                }
            }
        }
    }
}

enum class TabType {
    HOME, ALBUM, MAP, PROFILE
}


@Composable
fun MainScreen() {
    val (tabType, setTabType) = remember { mutableStateOf(TabType.MAP) }

    Scaffold(
        topBar = {
            TopBar(tabType)
        },
        bottomBar = {
            BottomBar(tabType, setTabType)
        }
    ) {
        Column(
            modifier = Modifier
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
