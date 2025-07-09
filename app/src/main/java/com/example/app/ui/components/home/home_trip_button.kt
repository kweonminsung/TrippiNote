package com.example.app.ui.components.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.app.R
import com.example.app.R.drawable.sample_image
import com.example.app.ui.components.popup.ContextMenu
import com.example.app.ui.pages.album.drawableResToByteArray
import com.example.app.ui.theme.CustomColors
import com.example.app.util.ObjectStorage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeTripButton(
    title: String,
    subtitle: String? = null,
    imageId: String? = null,
    onClick: () -> Unit,
    deleteFn: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val (expanded, setExpanded) = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .combinedClickable(
                onClick = onClick,
                onLongClick = {
                    setExpanded(true)
                },
            )
            .background(color = CustomColors.White)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (imageId != null) {
                    AsyncImage(
                        model = ObjectStorage.read(context, imageId),
                        contentDescription = "Planned Trip Image",
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .background(color = CustomColors.White)
                            .shadow(
                                elevation = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                            )
                            .clickable(onClick = onClick),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    val sampleImageByteArray = drawableResToByteArray(context, sample_image)
                    AsyncImage(
                        model = sampleImageByteArray,
                        contentDescription = "Sample Trip Image",
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .background(color = CustomColors.White)
                            .shadow(
                                elevation = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                            )
                            .clickable(onClick = onClick),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        color = CustomColors.Black,
                    )
                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            color = CustomColors.Gray,
                            modifier = Modifier.padding(top = 4.dp),
                        )
                    }
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