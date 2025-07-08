package com.example.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    leftBtnLabel: String, // 편집 or 완료
    leftBtnOnClick: ((Boolean) -> Unit) -> Unit = {},
    rightBtnLabel: String, // 닫기 or 삭제
    rightBtnOnClick: () -> Unit = {},
    content: @Composable () -> Unit = {} // 팝업창 내부에 추가할 내용
)
{
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    button{ setShowDialog(true) } // 팝업창 띄우는 버튼

    if (showDialog) {
        Dialog (onDismissRequest = { setShowDialog(false) }) {
            Surface (
                shape = RoundedCornerShape(8.dp),
                color = CustomColors.White,
                tonalElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column (
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton (onClick = {
                            leftBtnOnClick(setShowDialog)
                        }) {
                            Text(leftBtnLabel, color = CustomColors.Blue, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.weight(1f))  // 가운데 정렬 효과
                        Text(
                            text = title,
                            fontSize = 18.sp,
                            modifier = Modifier.align(Alignment.CenterVertically),
                            color = CustomColors.Black
                        )
                        Spacer(modifier = Modifier.weight(1f))  // 가운데 정렬 효과
                        TextButton(onClick = {
                            setShowDialog(false)
                            rightBtnOnClick()
                        }) {
                            Text(rightBtnLabel, color = CustomColors.Blue, fontSize = 14.sp)
                        }
                    }

                    content()
                }
            }
        }

    }
}