package com.example.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.TabType
import com.example.app.ui.theme.CustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    tabType: TabType,
    modifier: Modifier = Modifier
) {
    when (tabType) {
        TabType.HOME -> {
            TopAppBar(
                title = {
                    Text(
                        text = "홈",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp
                    )
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "설정",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CustomColors.White,
                    titleContentColor = CustomColors.Black,
                    actionIconContentColor = CustomColors.Black
                ),
            )
        }
        TabType.ALBUM -> {
            TopAppBar(
                title = {
                    Text(
                        text = "앨범",
                        fontSize = 21.sp
                    )
                },
                actions = {
                    Button(onClick = { /* TODO: 앨범 추가 */ }) {
                        Text("추가")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CustomColors.White,
                    titleContentColor = CustomColors.Black,
                    actionIconContentColor = CustomColors.Black
                ),
            )
        }
        TabType.MAP -> {
            SearchBar("검색 하고 있음", onQueryChange = { /* TODO: 검색어 변경 */ }, modifier = Modifier.fillMaxWidth())
        }
        TabType.PROFILE -> {
            TopAppBar(
                title = {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "프로필",
                            fontSize = 21.sp
                        )
                    }
                },
                actions = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CustomColors.White,
                    titleContentColor = CustomColors.Black,
                    actionIconContentColor = CustomColors.Black
                ),
            )
        }
    }
}

val onSettingsClick: () -> Unit = {
    println("Settings clicked")
}