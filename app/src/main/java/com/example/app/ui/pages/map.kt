package com.example.app.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.app.ui.components.SearchBar

@Composable
fun MapTab() {
    val (mapQuery, setMapQuery) = remember { mutableStateOf("") }

    Column {
        SearchBar(
            "원하는 장소 또는 여행 검색",
            mapQuery,
            onQueryChange = { setMapQuery(it) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}