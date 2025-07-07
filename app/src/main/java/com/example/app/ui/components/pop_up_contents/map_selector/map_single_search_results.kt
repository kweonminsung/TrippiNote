package com.example.app.ui.components.pop_up_contents.map_selector

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app.ui.pages.map.MapSearchResult
import com.example.app.ui.theme.CustomColors
import androidx.compose.material3.Text
import androidx.compose.ui.draw.shadow

@Composable
fun MapSingleSearchResults(
    searchResults: List<MapSearchResult>,
    onResultClick: (MapSearchResult) -> Unit,
    modifier: Modifier = Modifier
) {
    if (searchResults.isNotEmpty()) {
        Spacer(modifier = Modifier.height(4.dp))

        LazyColumn(
            modifier = modifier
                .width(300.dp)
                .heightIn(max = 300.dp)
                .shadow(
                    elevation = 1.dp,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(
                    CustomColors.White
                )
        ) {
            itemsIndexed(searchResults) { index, searchResult ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onResultClick(searchResult) }
                        .padding(10.dp)
                ) {
                    Text(
                        text = searchResult.title,
                        color = CustomColors.Black,
                    )
                }
                if (index != searchResults.lastIndex) {
                    Divider(
                        color = CustomColors.LightGray,
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}