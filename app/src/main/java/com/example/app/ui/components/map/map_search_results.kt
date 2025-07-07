package com.example.app.ui.components.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.app.ui.pages.map.MapSearchResult
import com.example.app.ui.theme.CustomColors
import com.google.android.libraries.places.api.model.AutocompletePrediction

@Composable
fun MapSearchResults(
    searchResults: Pair<List<MapSearchResult>, List<MapSearchResult>>,
    onDbResultClick: (MapSearchResult) -> Unit,
    onMapResultClick: (MapSearchResult) -> Unit,
    modifier: Modifier = Modifier
) {
    if (searchResults.first.isNotEmpty() || searchResults.second.isNotEmpty()) {
        Spacer(modifier = Modifier.height(4.dp))

        Column(
            modifier = modifier
                .width(300.dp)
                .heightIn(max = 300.dp)
                .shadow(
                    elevation = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    ambientColor = CustomColors.Black,
                    spotColor = CustomColors.Black
                )
                .background(
                    CustomColors.White
                )
        ) {
            Text(
                text = "여행에서 검색 결과",
                modifier = Modifier.padding(10.dp),
                color = CustomColors.Black
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                itemsIndexed(searchResults.first) { index, searchResult ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onDbResultClick(searchResult)
                            }
                            .padding(10.dp)
                    ) {
                        Row(

                        ) {
                            Text(
                                text = when(searchResult.type) {
                                    MapPinType.TRIP -> "여행"
                                    MapPinType.REGION -> "지역"
                                    MapPinType.SCHEDULE -> "일정"
                                    else -> "기타"
                                },
                                color = when (searchResult.type) {
                                    MapPinType.TRIP -> CustomColors.Blue
                                    MapPinType.REGION -> CustomColors.Green
                                    MapPinType.SCHEDULE -> CustomColors.Orange
                                    else -> CustomColors.Gray
                                },
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = searchResult.title,
                                color = CustomColors.Black
                            )
                        }

                    }
                    if (index != searchResults.second.lastIndex) {
                        Divider(
                            color = CustomColors.LightGray,
                            thickness = 1.dp
                        )
                    }
                }
            }

            Divider(
                color = CustomColors.LightGray,
                thickness = 2.dp
            )

            Text(
                text = "지도에서 검색 결과",
                modifier = Modifier.padding(10.dp),
                color = CustomColors.Black
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                itemsIndexed(searchResults.second) { index, searchResult ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onMapResultClick(searchResult)
                            }
                            .padding(10.dp)
                    ) {
                        Text(
                            text = searchResult.title,
                            color = CustomColors.Black
                        )
                    }
                    if (index != searchResults.second.lastIndex) {
                        Divider(
                            color = CustomColors.LightGray,
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
    }
}
