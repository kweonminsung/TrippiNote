package com.example.app.ui.components.pop_up_contents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.app.ui.components.PopupWindow
import com.example.app.ui.theme.CustomColors

@Composable
fun AddSampleContent() {
    Column {
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("이름을 입력하세요...", color = CustomColors.Gray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = CustomColors.LightGray,
                unfocusedContainerColor = CustomColors.White,
                focusedIndicatorColor = CustomColors.Gray,
                unfocusedIndicatorColor = CustomColors.DarkGray
            ),
            modifier = androidx.compose.ui.Modifier.fillMaxWidth()
        )
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