package com.example.app.ui.components


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
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
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
            ),
            modifier = Modifier
                .width(358.dp)
                .height(48.dp)
                .shadow(
                    elevation = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    ambientColor = CustomColors.Black,
                    spotColor = CustomColors.Black,
                ),
            singleLine = true,
            maxLines = 1
        )
    }

}