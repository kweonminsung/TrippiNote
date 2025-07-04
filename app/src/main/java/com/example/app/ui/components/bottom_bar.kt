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

@Composable
fun BottomBar(
    setTabType: (TabType) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        BottomBarButton("홈", R.drawable.bottombar_home, { setTabType(TabType.HOME)})
        BottomBarButton("사진", R.drawable.bottombar_album, { setTabType(TabType.ALBUM)})
        BottomBarButton("지도", R.drawable.bottombar_map, { setTabType(TabType.MAP)})
        BottomBarButton("프로필", R.drawable.bottombar_profile, { setTabType(TabType.PROFILE)})
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
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}