package com.example.app.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun BottomDrawer(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    if (isOpen) {
        val coroutineScope = rememberCoroutineScope()

        val drawerHeight = remember { mutableStateOf(0) }
        val offsetY = remember { Animatable(1f) }
        val dragOffset = remember { mutableStateOf(0f) }

        LaunchedEffect(isOpen) {
            offsetY.snapTo(1f) // 항상 처음은 아래에 있다가 올라오게
            offsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 350)
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        drawerHeight.value = coordinates.size.height
                    }
                    .offset {
                        val totalOffset = offsetY.value * drawerHeight.value + dragOffset.value
                        IntOffset(0, totalOffset.roundToInt())
                    }
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(
                            onVerticalDrag = { _, dragAmount ->
                                dragOffset.value += dragAmount
                            },
                            onDragEnd = {
                                if (dragOffset.value > 100f) {
                                    onDismiss()
                                } else {
                                    coroutineScope.launch {
                                        dragOffset.value = 0f
                                    }
                                }
                            }
                        )
                    }
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .zIndex(101f)
                    .padding(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(4.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(2.dp))
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    content()
                }
            }
        }
    }
}

