package com.example.app.ui.components.popup.map_selector

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.app.ui.components.map.MapPinType
import com.example.app.ui.components.search_bar.SearchBar
import com.example.app.ui.pages.map.MapSearchResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MapSingleSearchBar(
    query: String,
    onQueryChange: (String, Job?) -> Unit,
    onSearchResults: (List<MapSearchResult>) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    SearchBar(
        placeholder = "원하는 장소 또는 여행 검색",
        query = query,
        onQueryChange = { newQuery ->
            val searchJob = CoroutineScope(Dispatchers.IO).launch {
                delay(400)
                if (newQuery.isNotBlank()) {
                    try {
                        val results = PlaceUtil.searchPlaceByText(context, newQuery).map { prediction ->
                            MapSearchResult(
                                id = null,
                                type = MapPinType.SEARCH_RESULT,
                                title = prediction.getPrimaryText(null).toString(),
                                subtitle = prediction.getSecondaryText(null).toString(),
                                placeId = prediction.placeId
                            )
                        }
                        onSearchResults(results)
                    } catch (e: Exception) {
                        onSearchResults(emptyList())
                    }
                } else {
                    onSearchResults(emptyList())
                }
            }
            onQueryChange(newQuery, searchJob)
        },
        modifier = modifier.width(300.dp)
    )
}