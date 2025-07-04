package com.example.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.app.ui.theme.CustomColors

@Composable
fun SearchBar(
    placeholder: String,
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(placeholder) },
        modifier = modifier.fillMaxWidth()
            .shadow(
                elevation = 1.dp,
                shape = RectangleShape,
                ambientColor = CustomColors.Black,
                spotColor = CustomColors.Black,
            ),
        singleLine = true,
        maxLines = 1
    )
}