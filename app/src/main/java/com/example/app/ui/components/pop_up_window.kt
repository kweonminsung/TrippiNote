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
    button: @Composable (onClick: () -> Unit) -> Unit = {},
    title: String,
    label1: String, // 편집 or 완료
    label2: String, // 닫기 or 삭제
    tint: Color = CustomColors.Black,
    onSubmit: () -> Unit = {},
    onCancel: () -> Unit = {},
    content: @Composable () -> Unit = {} // 팝업창 내부에 추가할 내용
)
{
    var showDialog by remember { mutableStateOf(false) }

    // 팝업창을 띄우는 버튼
    button{ showDialog = true }

    if (showDialog) {
        Dialog (onDismissRequest = { showDialog = false }) {
            Surface (
                shape = RoundedCornerShape(8.dp),
                color = CustomColors.White,
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
                            onSubmit()
                        }) {
                            Text(label1, color = CustomColors.Blue, fontSize = 14.sp)
                        }

                        Spacer(modifier = Modifier.weight(1f))  // 가운데 정렬 효과

                        Text(
                            text = title,
                            fontSize = 18.sp,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )

                        Spacer(modifier = Modifier.weight(1f))  // 가운데 정렬 효과

                        TextButton(onClick = {
                            showDialog = false
                            onCancel()
                        }) {
                            Text(label2, color = CustomColors.Blue, fontSize = 14.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    content()

                }
            }
        }

    }
}