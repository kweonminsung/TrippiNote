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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.LocalSession
import com.example.app.ui.components.animations.FireworkDialog
import com.example.app.ui.components.profile.ProfileCard
import com.example.app.ui.components.profile.ProfileCountBox
import com.example.app.ui.components.profile.ProfileSpecific
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

    val (specificOpen, setSpecificOpen) = remember { mutableStateOf(false) }

    if (specificOpen) {
        ProfileSpecific(
            onDismiss = { setSpecificOpen(false) },
        )
    }

    val (showFireworks, setShowFireworks) = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = CustomColors.LighterGray
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 30.dp,
                    vertical = 8.dp
                )
        ) {
            ProfileCard(
                name = username,
                email = email,
                onClick = { setSpecificOpen(true) },
                image_id = null
            )

            Text(
                text = "여행 상태",
                color = CustomColors.Black,
                fontSize = 20.sp,
                modifier = Modifier.padding(
                    top = 32.dp,
                    bottom = 16.dp
                )
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
                        setShowFireworks(true)
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.weight(0.1f))
                ProfileCountBox(
                    count = regionsCount,
                    label = "Regions",
                    onClick = {
                        setShowFireworks(true)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if(showFireworks) {
            FireworkDialog(
                onDismissRequest = { setShowFireworks(false) },
                autoDismissMillis = 2000L
            )
        }
    }
}