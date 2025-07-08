package com.example.app.ui.components.top_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.CustomColors
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.app.ui.components.popup.AddTripForm
import com.example.app.util.DatetimeUtil
import com.example.app.util.database.MapRepository
import com.example.app.util.database.model

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    username: String,
) {
    val context = LocalContext.current

    TopAppBar(
        title = {
            Text(
                text = "${username}의 여행 다이어리",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp),
                fontSize = 20.sp
            )
        },
        actions = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                AddTripForm(
                    button = { onClick ->
                        IconButton(onClick = onClick) {
                            Icon(Icons.Default.Add, contentDescription = "추가")
                        }
                    },
                    saveFn = { title, start_date, end_date, locValue ->
                        MapRepository.createTrip(context, model.Trip(
                            id = -1,
                            title = title,
                            start_date = start_date,
                            end_date = end_date,
                            lat = locValue.position.latitude,
                            lng = locValue.position.longitude,
                            created_at = DatetimeUtil.getCurrentDatetime(),
                        ))
                    },
                    title = "새 여행 추가",
                )
            }

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CustomColors.LighterGray,
            titleContentColor = CustomColors.Black,
            actionIconContentColor = CustomColors.Black
        ),
    )
}