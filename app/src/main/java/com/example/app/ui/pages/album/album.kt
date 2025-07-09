package com.example.app.ui.pages.album


import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.app.ui.theme.CustomColors
import com.example.app.util.database.model
import java.io.File
import coil.compose.AsyncImage
import com.example.app.util.ObjectStorage.save
import com.example.app.util.ObjectStorage.read
import com.example.app.util.database.ImageResult
import com.example.app.util.database.MapRepository.createScheduleImage
import com.example.app.util.database.MapRepository.getScheduleImages
import com.example.app.util.database.MapRepository.getRandomTripImages
import com.example.app.util.database.MapRepository.getRandomRegionImages
import com.example.app.util.database.MapRepository.getRandomScheduleImages
import com.example.app.R.drawable.sample_image
import kotlin.collections.chunked
import kotlin.collections.forEach



val imageSize = 150.dp
val folderWidth = 230.dp
val totalRowWidth = folderWidth * 2 + 8.dp
val fontSize = 15.sp
val popUpSize = 250
fun drawableResToByteArray(context: Context, resId: Int): ByteArray? {
    return context.resources.openRawResource(resId).use { it.readBytes() }
}

fun uploadImage(
    context: Context,
    file: File,
    schdule: model.Schedule
){
    val file_id = save(context, file.readBytes()).toString()
    createScheduleImage(context, schdule.id,file_id)
}

@Composable
fun ImagePopup(
    context: Context,
    fileId: String,
    onDismiss: () -> Unit
) {
    val imageBytes = try {
        read(context, fileId)
    } catch (e: Exception) {
        Log.e("ImagePopup", "Failed to read image: ${e.message}")
        null
    }

    if (imageBytes != null) {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true  // 이미지 실제로 디코딩하지 않고 사이즈만 가져옴
        }
        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size, options)

        val imageWidth: Float
        val imageHeight: Float

        if(options.outWidth < options.outHeight) {
            imageHeight = ((options.outHeight.toFloat()/options.outWidth)*popUpSize)
            imageWidth = popUpSize.toFloat()
        } else {
            imageHeight = popUpSize.toFloat()
            imageWidth = ((options.outWidth.toFloat()/options.outHeight)*popUpSize)
        }
        Dialog(onDismissRequest = { onDismiss() }) {
            Box(
                modifier = Modifier
                    .width(imageWidth.dp)
                    .height(imageHeight.dp)
                    .clickable { onDismiss() },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageBytes,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}


@Composable
fun FolderItem( // 앨범 폴더 썸네일 + 이름 + 클릭 이벤트
    name: String,
    thumbnail: ByteArray?,
    context: Context = LocalContext.current,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(2.dp)
    ) {
        if (thumbnail != null) {
            AsyncImage(
                model = thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = sample_image, // 기본 이미지
                contentDescription = null,
                modifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            name,
            fontSize = 17.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = CustomColors.Black,
        )
    }
}


@Composable
fun TripFolderGridColumn(
    context: Context,
    onTripFolderClick: (ImageResult) -> Unit
) {
    val sampleImageByteArray = drawableResToByteArray(context, sample_image)
    val images: List<ImageResult> = getRandomTripImages(context)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.LighterGray)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            if (images.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "여행 기록이 없습니다",
                        color = CustomColors.DarkGray,
                        fontSize = 18.sp
                    )
                }
            } else {
                // 폴더 아이템들 정렬
                var thumbnail: ByteArray?

                images.chunked(2).forEach { imageChuck ->
                    Row(
                        modifier = Modifier
                            .width(totalRowWidth)
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        imageChuck.forEach { image ->
                            if (image.file_id == null) {
                                thumbnail = sampleImageByteArray // 기본 이미지
                            } else {
                                thumbnail = try {
                                    if (image.file_id != null) read(
                                        context,
                                        image.file_id
                                    ) else sampleImageByteArray
                                } catch (e: Exception) {
                                    Log.e("ThumbnailRead", "Error reading image: ${e.message}")
                                    sampleImageByteArray
                                }
                            }
                            FolderItem(
                                name = image.title,
                                thumbnail = thumbnail,
                                onClick = { onTripFolderClick(image) },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 4.dp)
                            )
                        }

                        // 마지막 줄에 짝이 하나면 Spacer로 채우기
                        if (imageChuck.size < 2) {
                            Spacer(modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))
                }
            }

        }
    }
}

@Composable
fun RegionFolderGridColumn(
    context: Context,
    trip: model.Trip,
    onRegionFolderClick: (ImageResult) -> Unit
) {
    val sampleImageByteArray = drawableResToByteArray(context, sample_image)
    var images: List<ImageResult> = getRandomRegionImages(context, trip.id)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.LighterGray)
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            if (images.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "여행 기록이 없습니다",
                        color = CustomColors.DarkGray,
                        fontSize = 18.sp
                    )
                }
            } else {
                // 폴더 아이템들 정렬
                var thumbnail: ByteArray?

                images.chunked(2).forEach { imageChuck ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        imageChuck.forEach { image ->
                            if (image.file_id == null) {
                                thumbnail = sampleImageByteArray
                            } else {
                                thumbnail = try {
                                    if (image.file_id != null) read(
                                        context,
                                        image.file_id
                                    ) else sampleImageByteArray
                                } catch (e: Exception) {
                                    Log.e("ThumbnailRead", "Error reading image: ${e.message}")
                                    sampleImageByteArray
                                }
                            }
                            FolderItem(
                                name = image.title,
                                thumbnail = thumbnail,
                                onClick = { onRegionFolderClick(image) },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 4.dp)
                            )
                        }

                        // 마지막 줄에 짝이 하나면 Spacer로 채우기
                        if (imageChuck.size < 2) {
                            Spacer(modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

        }
    }
}

@Composable
fun ScheduleFolderGridColumn(
    context: Context,
    region: model.Region,
    onScheduleFolderClick: (ImageResult) -> Unit
) {
    val sampleImageByteArray = drawableResToByteArray(context, sample_image)
    var images: List<ImageResult> = getRandomScheduleImages(context, region.id)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.LighterGray)
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            if (images.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "여행 기록이 없습니다",
                        color = CustomColors.DarkGray,
                        fontSize = 18.sp
                    )
                }
            } else {
                // 폴더 아이템들 정렬
                var thumbnail: ByteArray?

                images.chunked(2).forEach { imageChuck ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        imageChuck.forEach { image ->
                            if (image.file_id == null) {
                                thumbnail = sampleImageByteArray
                            } else {
                                thumbnail = try {
                                    if (image.file_id != null) read(
                                        context,
                                        image.file_id
                                    ) else sampleImageByteArray
                                } catch (e: Exception) {
                                    Log.e("ThumbnailRead", "Error reading image: ${e.message}")
                                    sampleImageByteArray
                                }
                            }
                            FolderItem(
                                name = image.title,
                                thumbnail = thumbnail,
                                onClick = { onScheduleFolderClick(image) },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 4.dp)
                            )
                        }

                        // 마지막 줄에 짝이 하나면 Spacer로 채우기
                        if (imageChuck.size < 2) {
                            Spacer(modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

        }
    }
}


@Composable
fun ScheduleImageGrid(
    context: Context,
    schedule: model.Schedule,
    onImageClick: (ImageResult) -> Unit = {}
) {
    val sampleImageByteArray = drawableResToByteArray(context, sample_image)

    val schedule_images = getScheduleImages(context, schedule.id)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.LighterGray)
            .padding(2.dp),
    ) {
        if (schedule_images.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "사진 기록이 없습니다",
                    color = CustomColors.DarkGray,
                    fontSize = 18.sp
                )
            }
            return
        } else {
            Column {
                schedule_images.chunked(3).forEach { scheduleChuck ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        scheduleChuck.forEach { image ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f), // 정사각형 박스
                                contentAlignment = Alignment.Center
                            ) {
                                if (image.file_id == null) {
                                    AsyncImage(
                                        model = sampleImageByteArray,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(1.dp)
                                    )
                                } else {
                                    AsyncImage(
                                        model = try {
                                            if (image.file_id != null) read(
                                                context,
                                                image.file_id
                                            ) else sampleImageByteArray
                                        } catch (e: Exception) {
                                            Log.e("ThumbnailRead", "Error reading image: ${e.message}")
                                            sampleImageByteArray
                                        },
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clickable {
                                                onImageClick(image)
                                            }
                                            .padding(1.dp)
                                    )

                                }
                            }
                        }

                        // 요소가 1개나 2개만 있는 마지막 줄 처리
                        repeat(3 - scheduleChuck.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

