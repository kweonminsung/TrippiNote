package com.example.app.ui.components.pop_up_contents

import androidx.compose.foundation.background
import com.example.app.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.app.ui.components.PopupWindow
import com.example.app.ui.theme.CustomColors
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import kotlin.Boolean


//@Composable
//fun LocationPickerMap(
//    onLocationPicked: (LatLng) -> Unit
//) {
//    val singapore = LatLng(1.35, 103.87)
//    var pickedLocation by remember { mutableStateOf(singapore) }
//
//    GoogleMap (
//        modifier = Modifier.fillMaxSize(),
//        onMapClick = {
//            pickedLocation = it
//            onLocationPicked(it)
//        }
//    ) {
//        Marker(position = pickedLocation)
//    }
//}

@Composable
fun AddSampleContent() {
    Box(){
        //여행 이름
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("여행 이름") },
            placeholder = { Text("이름을 입력하세요...", color = CustomColors.Gray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = CustomColors.LightGray,
                unfocusedContainerColor = CustomColors.White,
                focusedIndicatorColor = CustomColors.Gray,
                unfocusedIndicatorColor = CustomColors.DarkGray
            ),
            modifier = androidx.compose.ui.Modifier.fillMaxWidth()
        )
    }
        //지도에서 선택
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 위치 보이는 필드
        TextField(
            value = "",
            onValueChange = {  },
            modifier = Modifier.weight(1f), // TextField가 남는 공간 채움
            placeholder = { Text("위치를 선택하세요...", color = CustomColors.Gray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = CustomColors.LightGray,
                unfocusedContainerColor = CustomColors.White,
                focusedIndicatorColor = CustomColors.Gray,
                unfocusedIndicatorColor = CustomColors.DarkGray
            ),
            readOnly = true
        )

        Spacer(modifier = Modifier.width(8.dp)) // 여백

        // 지도에서 위치 선택 버튼
        IconButton(
            onClick = {},
            modifier = Modifier
                .height(45.dp)
                .width(45.dp)
                .background(
                    color = CustomColors.White,
                    shape = RoundedCornerShape(4.dp)
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.map_etc),
                contentDescription = "추가"
            )
        }
    }

}

@Composable
fun AddSampleForm(
    button: @Composable (onClick: () -> Unit) -> Unit = {},
    title: String,
    onSubmit: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    PopupWindow(
        button,
        title,
        "완료", // 편집 or 완료
        "닫기", // 닫기 or 삭제
        CustomColors.Blue,
        onSubmit,
        onCancel,
        { AddSampleContent() }// 팝업창 내부에 추가할 내용
    )
}