package com.example.app.ui.components.search_bar


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.app.ui.theme.CustomColors
import com.example.app.R
import androidx.compose.ui.res.painterResource

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
        placeholder = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.searchbar_search),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(placeholder)
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = CustomColors.White,
            unfocusedContainerColor = CustomColors.White,
            focusedPlaceholderColor = CustomColors.Gray,
            unfocusedPlaceholderColor = CustomColors.Gray,
            focusedTextColor = CustomColors.Black,
            unfocusedTextColor = CustomColors.Black,
            focusedIndicatorColor = CustomColors.Transparent,
            unfocusedIndicatorColor = CustomColors.Transparent,
            disabledIndicatorColor = CustomColors.Transparent,
            errorIndicatorColor = CustomColors.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(48.dp)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(8.dp),
                ambientColor = CustomColors.Black,
                spotColor = CustomColors.Black
            ),
        singleLine = true,
        maxLines = 1
    )
}
