package com.example.app.ui.pages.album

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.app.ui.components.search_bar.SearchBar
import com.example.app.ui.components.top_bar.AlbumTopBar

@Composable
fun AlbumTab() {
    Column {
        AlbumTopBar(
            username = "사용자 이름" // Replace with actual username
        )
        SearchBar(
            placeholder = "원하는 장소 또는 여행 검색",
            query = "",
            onQueryChange = {}
        )
    }
}