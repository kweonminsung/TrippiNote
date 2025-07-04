package com.example.app.ui.components.top_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.app.ui.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapTopBar(
    modifier: Modifier = Modifier
) {
    val (mapQuery, setMapQuery) = remember { mutableStateOf("") }


    SearchBar(
        "원하는 장소 또는 여행 검색",
        mapQuery,
        onQueryChange = { setMapQuery(it) },
        modifier = Modifier.fillMaxWidth()
    )
}