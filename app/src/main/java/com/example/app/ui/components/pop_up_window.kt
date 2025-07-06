package com.example.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.app.ui.theme.CustomColors

@Composable
fun PopupWindow(
    icon: @Composable () -> Unit,
    title: String,
    label: String,
    placeholder: String,
    tint: Color = CustomColors.Black,
    transform: @Composable () -> Unit = {} // 팝업창에 추가할 함수...
)
{
    var showDialog by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }

    IconButton (onClick = { showDialog = true }) {
        icon()
    }
    if (showDialog) {
        Dialog (onDismissRequest = { showDialog = false }) {
            Surface (
                shape = RoundedCornerShape(8.dp),
                tonalElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column (modifier = Modifier.padding(16.dp)) {

                    // 타이틀 + 버튼 행
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton (onClick = {
                            showDialog = false
                            inputText = ""
                        }) {
                            Text("닫기")
                        }

                        Spacer(modifier = Modifier.weight(1f))  // 가운데 정렬 효과

                        Text(
                            text = title,
                            fontSize = 18.sp,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )

                        Spacer(modifier = Modifier.weight(1f))  // 가운데 정렬 효과

                        TextButton(onClick = {
                            println("여행 이름: $inputText")
                            showDialog = false
                            inputText = ""
                        }) {
                            Text(label)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))


//                    )// 텍스트 필드
////                    TextField(
////                        value = inputText,
////                        onValueChange = { inputText = it },
////                        placeholder = { Text(placeholder) },
////                        modifier = Modifier.fillMaxWidth()
                }
            }
        }

    }
}