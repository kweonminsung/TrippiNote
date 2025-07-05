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
import com.example.app.ui.theme.AppTheme
import com.example.app.ui.pages.album.AlbumTab
import com.example.app.ui.pages.HomeTab
import com.example.app.ui.pages.map.MapTab
import com.example.app.ui.pages.profile.ProfileTab
import com.example.app.ui.components.BottomBar
import com.example.app.ui.components.top_bar.TopBar
import com.example.app.util.KeyValueStore
import com.google.android.libraries.places.api.Places
//import com.example.app.ui.pages.album


data class SessionData(val data: Map<String, Any>)

val EXAMPLE_SESSION_DATA: Map<String, Any> = mapOf(
    "user" to mapOf(
        "name" to "John Doe",
        "email" to "example@test.com",
        "phone" to "123-456-7890",
        "passport_number" to "X123456789",
        "passport_expiry" to "2025-12-31",
        "passport_object_id" to "passport_12345",
    ),
    "wishlist" to listOf(
        mapOf(
            "done" to true,
            "content" to "Visit the Eiffel Tower",
        ),
        mapOf(
            "done" to false,
            "content" to "See the Northern Lights",
        )
    ),
    "checklist" to listOf(
        mapOf(
            "done" to true,
            "content" to "Book flight tickets",
        ),
        mapOf(
            "done" to false,
            "content" to "Reserve hotel room",
        )
    ),
    "trips" to listOf(
        mapOf(
            "destination" to "Paris",
            "start_date" to "2024-05-01",
            "end_date" to "2024-05-10",
            "notes" to "Visit the Louvre and enjoy French cuisine.",
            "created_at" to "2024-04-01T12:00:00Z",
        ),
        mapOf(
            "id" to "trip_002",
            "destination" to "Tokyo",
            "start_date" to "2024-06-15",
            "end_date" to "2024-06-25",
            "notes" to "Explore Shibuya and try sushi.",
            "created_at" to "2024-05-01T08:30:00Z",
        )
    ),
)

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

        val apiKey = getMetaDataValue("com.google.android.geo.API_KEY")
        if (!Places.isInitialized() && apiKey != null) {
            Places.initialize(applicationContext, apiKey)
        }

        setContent {
            val context = this

            KeyValueStore.clear(context)

            // 예시 데이터 삽입
            KeyValueStore.saveBulk(context, EXAMPLE_SESSION_DATA)

//            KeyValueStore.getAll(context).forEach { (key, value) ->
//                Log.d("KeyValueStore", "Key: $key, Value: $value")
//            }

            // KeyValueStore에서 가져온 데이터를 LocalSession에 저장
            val initialSessionData = KeyValueStore.getAll(context)
            val session = remember { mutableStateOf(SessionData(initialSessionData)) }

            Log.d("SessionData", "Initial Session Data: ${session.value.data}")

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
