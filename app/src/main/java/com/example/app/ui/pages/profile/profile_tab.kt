package com.example.app.ui.pages.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.app.LocalSession
import com.example.app.ui.components.profile.ProfileCard
import com.example.app.ui.components.profile.ProfileCountBox
import com.example.app.ui.theme.CustomColors
import com.example.app.util.database.MapRepository

@Composable
fun ProfileTab() {
    val context = LocalContext.current

    val sessionData = LocalSession.current.value
    val username = sessionData.user.name
    val email = sessionData.user.email

    val tripsCount = MapRepository.countTrips(context)
    val regionsCount = MapRepository.countRegions(context)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = CustomColors.LighterGray
            ),
        contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 30.dp,
                    vertical = 16.dp
                )
        ) {
            ProfileCard(
                name = username,
                email = email,
                image_id = null
            )

            Text(
                text = "여행 상태",
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileCountBox(
                    count = tripsCount,
                    label = "Trips",
                    onClick = {

                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.weight(0.1f))
                ProfileCountBox(
                    count = regionsCount,
                    label = "Regions",
                    onClick = {},
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}