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

const val BOTTOM_DRAWER_ANIMATION_DURATION = 200 // BottomDrawer 애니메이션 시간
@Composable
fun BottomDrawer(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val isVisible = remember { mutableStateOf(isOpen) }
    val coroutineScope = rememberCoroutineScope()
    val drawerHeight = remember { mutableIntStateOf(0) }
    val offsetY = remember { Animatable(1f) }
    val dragOffset = remember { mutableFloatStateOf(0f) }

    LaunchedEffect(isOpen) {
        if (isOpen) {
            isVisible.value = true

            offsetY.snapTo(1f) // offsetY를 항상 1f로 초기화
            dragOffset.value = 0f // 드래그 오프셋도 초기화
            offsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = BOTTOM_DRAWER_ANIMATION_DURATION)
            )
        } else {
            offsetY.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = BOTTOM_DRAWER_ANIMATION_DURATION)
            )
            isVisible.value = false
        }
    }

    if (isVisible.value) {
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
                        val totalOffset = offsetY.value * drawerHeight.value + dragOffset.value.coerceAtLeast(0f)
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
