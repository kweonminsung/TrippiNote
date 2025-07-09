package com.example.app.ui.components.map

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.R
import com.example.app.ui.components.popup.ContextMenu
import com.example.app.ui.theme.CustomColors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegionInfoButton(
    onClick: () -> Unit = {},
    deleteFn: () -> Unit = {},
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    val (expanded, setExpanded) = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(8.dp)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .background(color = CustomColors.White, shape = RoundedCornerShape(8.dp))
            .combinedClickable(
                onClick = onClick,
                onLongClick = {
                    setExpanded(true)
                },
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = CustomColors.Black,
                    fontSize = 16.sp,
                )
                subtitle?.let {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = it,
                        color = CustomColors.Gray
                    )
                }
            }
            Image(
                painter = painterResource(id = R.drawable.button_rightarrow),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }

        ContextMenu(
            topLabel = "삭제",
            bottomLabel = "취소",
            onClickTop = {
                deleteFn()
                setExpanded(false)
            },
            onClickBottom = {
                setExpanded(false)
            },
            expanded = expanded,
            onDismiss = { setExpanded(false) },
        )
    }
}