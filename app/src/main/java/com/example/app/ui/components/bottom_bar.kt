package com.example.app.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import com.example.app.R
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.TabType
import com.example.app.ui.theme.CustomColors

@Composable
fun BottomBar(
    tabType: TabType,
    setTabType: (TabType) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .shadow(
                elevation = 24.dp,
                ambientColor = CustomColors.Black,
                spotColor = CustomColors.Black
            )
            .background(CustomColors.White),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarButton(
            label = "홈",
            iconResId = if (tabType == TabType.HOME) R.drawable.bottombar_home_fill else R.drawable.bottombar_home_empty,
            isSelected = tabType == TabType.HOME,
            onClick = { setTabType(TabType.HOME) }
        )

        BottomBarButton(
            label = "사진",
            iconResId = if (tabType == TabType.ALBUM) R.drawable.bottombar_album_fill else R.drawable.bottombar_album_empty,
            isSelected = tabType == TabType.ALBUM,
            onClick = { setTabType(TabType.ALBUM) }
        )

        BottomBarButton(
            label = "지도",
            iconResId = if (tabType == TabType.MAP) R.drawable.bottombar_map_fill else R.drawable.bottombar_map_empty,
            isSelected = tabType == TabType.MAP,
            onClick = { setTabType(TabType.MAP) }
        )

        BottomBarButton(
            label = "프로필",
            iconResId = if (tabType == TabType.PROFILE) R.drawable.bottombar_profile_fill else R.drawable.bottombar_profile_empty,
            isSelected = tabType == TabType.PROFILE,
            onClick = { setTabType(TabType.PROFILE) }
        )
    }
}

@Composable
fun BottomBarButton(
    label: String,
    iconResId: Int,
    isSelected: Boolean,
    onClick: () -> Unit = {},
) {
    val animatedIconSize = animateDpAsState(
        targetValue = if (isSelected) 28.dp else 24.dp,
        label = "iconSize"
    )

    val animatedFontSize = animateFloatAsState(
        targetValue = if (isSelected) 11f else 10f,
        label = "fontSize"
    )


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = label,
            modifier = Modifier.size(animatedIconSize.value)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = animatedFontSize.value.sp,
            color = CustomColors.DarkGray
        )
    }
}
