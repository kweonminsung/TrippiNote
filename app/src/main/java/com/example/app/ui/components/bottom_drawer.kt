package com.example.app.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

const val BOTTOM_DRAWER_ANIMATION_DURATION = 200L // BottomDrawer 애니메이션 시간
@Composable
fun BottomDrawer(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    if (isOpen) {
        val coroutineScope = rememberCoroutineScope()

        val drawerHeight = remember { mutableIntStateOf(0) }
        val offsetY = remember { Animatable(1f) }
        val dragOffset = remember { mutableFloatStateOf(0f) }

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
                    .heightIn(max = 300.dp)
                    .onGloballyPositioned { coordinates ->
                        drawerHeight.value = coordinates.size.height
                    }
                    .offset {
                        val totalOffset = offsetY.value * drawerHeight.value + dragOffset.value.coerceAtLeast(0f) // dragOffset이 음수(위로 올림)일 때 0 이하로 못 가게 제한
                        IntOffset(0, totalOffset.roundToInt())
                    }
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(
                            onVerticalDrag = { _, dragAmount ->
                                dragOffset.value = (dragOffset.value + dragAmount).coerceAtLeast(0f)
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
                    .zIndex(99f)
                    .padding(
                        top = 12.dp,
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        )
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(4.dp)
                            .background(Color.DarkGray, shape = RoundedCornerShape(2.dp))
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    content()
                }
            }
        }
    }
}
