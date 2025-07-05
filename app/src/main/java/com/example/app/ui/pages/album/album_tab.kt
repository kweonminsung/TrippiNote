package com.example.app.ui.pages.album

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.app.ui.components.SearchBar
import com.example.app.ui.components.top_bar.AlbumTopBar

@Composable
fun AlbumTab() {
    Column {
        AlbumTopBar(
            username = "사용자 이름" // Replace with actual username
        )
        SearchBar()
    }
}