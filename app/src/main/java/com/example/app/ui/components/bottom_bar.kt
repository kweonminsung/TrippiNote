package com.example.app.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BottomBar(
    onItemClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
//        BottomBarButton("홈", Icons.Default.Home) { onItemClick("home") }
//        BottomBarButton("사진", Icons.Default.Phone) { onItemClick("photos") }
//        BottomBarButton("지도", Icons.Default.Add) { onItemClick("add") }
//        BottomBarButton("프로필", Icons.Default.Person) { onItemClick("profile") }
    }
}

//@Composable
//fun IconButtonExample() {
//    Button(
//        onClick = { /* 버튼 클릭 시 동작 */ },
//        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFEEEEEE))
//    ) {
//        Icon(
//            imageVector = ImageVector.vectorResource(id = app.src.res.drawable.ic_home),
//            contentDescription = "홈 아이콘",
//            modifier = Modifier.size(20.dp)
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Text("홈")
//    }
//}