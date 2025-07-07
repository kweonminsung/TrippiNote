package com.example.app.ui.components.pop_up_contents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.example.app.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Place
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
fun AddSampleContent(
    name_label: String = "",
    map_label: String = "",
    start_label: String = "",
    end_label: String = "",
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(60.dp) // 원하는 너비로 조정
            .padding(8.dp)
    ){

        Spacer(modifier = Modifier.height(8.dp)) // 여백

        //여행 이름
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("이름을 입력하세요...", color = CustomColors.Gray) },
            leadingIcon = {
                Text(
                    text = name_label,
                    modifier = Modifier.padding(start = 8.dp),
                    color = CustomColors.Black
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = CustomColors.LightGray,
                unfocusedContainerColor = CustomColors.LighterGray,
                focusedIndicatorColor =  CustomColors.White,
                unfocusedIndicatorColor = CustomColors.White
            ),
            modifier = androidx.compose.ui.Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp)) // 여백

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
                // placeholder = { Text("위치를 선택하세요...", color = CustomColors.Gray) },
                leadingIcon = {
                    Text(
                        text = map_label,
                        modifier = Modifier.padding(start = 8.dp),
                        color = CustomColors.Black
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = CustomColors.LightGray,
                    unfocusedContainerColor = CustomColors.LighterGray,
                    focusedIndicatorColor =  CustomColors.White,
                    unfocusedIndicatorColor = CustomColors.White
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
            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = "추가"
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp)) // 여백

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
        { AddSampleContent("여행 이름", "위치", ) }// 팝업창 내부에 추가할 내용
    )
}