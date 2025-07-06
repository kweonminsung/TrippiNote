package com.example.app.ui.components

import com.example.app.R
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            .background(CustomColors.White)
            .padding(
                top = 6.dp,
                bottom = 8.dp,
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        BottomBarButton("홈", if (tabType == TabType.HOME) R.drawable.bottombar_home_fill else R.drawable.bottombar_home_empty , { setTabType(TabType.HOME)})
        BottomBarButton("사진", if (tabType == TabType.ALBUM) R.drawable.bottombar_album_fill else R.drawable.bottombar_album_empty, { setTabType(TabType.ALBUM)})
        BottomBarButton("지도", if (tabType == TabType.MAP) R.drawable.bottombar_map_fill else R.drawable.bottombar_map_empty, { setTabType(TabType.MAP)})
        BottomBarButton("프로필", if (tabType == TabType.PROFILE) R.drawable.bottombar_profile_fill else R.drawable.bottombar_profile_empty, { setTabType(TabType.PROFILE)})
    }
}

@Composable
fun BottomBarButton(
    label: String,
    iconResId: Int,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = label,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = Color.DarkGray
        )
    }
}