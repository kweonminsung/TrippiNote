package com.example.app.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.app.ui.components.search_bar.SearchBar
import com.example.app.ui.theme.CustomColors

@Composable
fun HomeTab() {
    var query by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,  // 클릭 효과 제거
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()  // 👉 키보드 내리기
            }
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ){
        Column(modifier = Modifier
            .width(600.dp)
            .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {



        }
    }
}