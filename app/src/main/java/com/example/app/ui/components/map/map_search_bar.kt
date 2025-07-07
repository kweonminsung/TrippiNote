package com.example.app.ui.components.map

import android.util.Log
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.app.ui.components.search_bar.SearchBar
import com.example.app.ui.pages.map.MapSearchResult
import com.example.app.util.database.MapRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MapSearchBar(
    query: String,
    onQueryChange: (String, Job?) -> Unit,
    onSearchResults: (Pair<List<MapSearchResult>, List<MapSearchResult>>) -> Unit,
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
                        val dbResults = MapRepository.searchFromAll(context, newQuery).map { result ->
                            MapSearchResult(
                                id = result.id,
                                type = result.type,
                                title = result.title
                            )
                        }
                        Log.d("MapSearchBar", "DB Results: $dbResults")
                        val mapResults = PlaceUtil.searchPlaceByText(context, newQuery).map { prediction ->
                            MapSearchResult(
                                id = null,
                                type = MapPinType.SEARCH_RESULT,
                                title = prediction.getPrimaryText(null).toString(),
                                subtitle = prediction.getSecondaryText(null).toString(),
                                placeId = prediction.placeId
                            )
                        }
                        onSearchResults(Pair(dbResults, mapResults))
                    } catch (e: Exception) {
                        onSearchResults(Pair(emptyList(), emptyList()))
                    }
                } else {
                    onSearchResults(Pair(emptyList(), emptyList()))
                }
            }
            onQueryChange(newQuery, searchJob)
        },
        modifier = modifier.width(300.dp)
    )
}
