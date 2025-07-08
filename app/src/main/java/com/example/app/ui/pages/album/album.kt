package com.example.app.ui.pages.album


import android.content.Context
import android.graphics.drawable.Icon
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.app.ui.theme.CustomColors
import com.example.app.util.database.model
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import coil.compose.AsyncImage
import com.example.app.util.ObjectStorage.save
import com.example.app.util.ObjectStorage.read
import com.example.app.util.database.ImageResult
import com.example.app.util.database.MapRepository.createScheduleImage
import com.example.app.ui.pages.getAllTrips
import com.example.app.util.database.MapRepository.getRegions
import com.example.app.util.database.MapRepository.getSchedules
import com.example.app.util.database.MapRepository.getScheduleImages
import com.example.app.util.database.MapRepository.getRandomTripImages
import com.example.app.util.database.MapRepository.getRandomRegionImages
import com.example.app.util.database.MapRepository.getRandomScheduleImages
import com.example.app.R.drawable.sample_image
import kotlin.collections.chunked
import kotlin.collections.forEach


fun drawableResToByteArray(context: Context, resId: Int): ByteArray? {
    return context.resources.openRawResource(resId).use { it.readBytes() }
}

fun UploadImage(
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
        Dialog(onDismissRequest = { onDismiss() }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(CustomColors.LightGray)
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
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        if (thumbnail != null) {
            AsyncImage(
                model = thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
        } else {
            AsyncImage(
                model = sample_image, // 기본 이미지
                contentDescription = null,
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(name, fontSize = 18.sp)
    }
}


@Composable
fun TripFolderGridColumn(
    context: Context,
    onTripFolderClick: (ImageResult) -> Unit
) {
    val sampleImageByteArray = drawableResToByteArray(context, sample_image)

    var images: List<ImageResult> = getRandomTripImages(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
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
                        .fillMaxWidth()
                        .padding(2.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    imageChuck.forEach { image ->
                        if(image.file_id == null) {
                            thumbnail = sampleImageByteArray // 기본 이미지
                        } else {
                            thumbnail = try {
                                if (image.file_id != null) read(context, image.file_id) else sampleImageByteArray
                            } catch (e: Exception) {
                                Log.e("ThumbnailRead", "Error reading image: ${e.message}")
                                sampleImageByteArray
                            }
                        }
                        FolderItem(
                            name = image.title,
                            thumbnail = thumbnail,
                            onClick = { onTripFolderClick(image) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // 마지막 줄에 짝이 하나면 Spacer로 채우기
                    if (imageChuck.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
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
                        if(image.file_id == null) {
                            thumbnail = sampleImageByteArray
                        } else {
                            thumbnail = try {
                                if (image.file_id != null) read(context, image.file_id) else sampleImageByteArray
                            } catch (e: Exception) {
                                Log.e("ThumbnailRead", "Error reading image: ${e.message}")
                                sampleImageByteArray
                            }
                        }
                        FolderItem(
                            name = image.title,
                            thumbnail = thumbnail,
                            onClick = { onRegionFolderClick(image) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // 마지막 줄에 짝이 하나면 Spacer로 채우기
                    if (imageChuck.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
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
                        if(image.file_id == null) {
                            thumbnail = sampleImageByteArray // 기본 이미지
                        } else {
                            thumbnail = try {
                                if (image.file_id != null) read(context, image.file_id) else sampleImageByteArray
                            } catch (e: Exception) {
                                Log.e("ThumbnailRead", "Error reading image: ${e.message}")
                                sampleImageByteArray
                            }
                        }
                        FolderItem(
                            name = image.title,
                            thumbnail = thumbnail,
                            onClick = { onScheduleFolderClick(image) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // 마지막 줄에 짝이 하나면 Spacer로 채우기
                    if (imageChuck.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
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

    if(schedule_images.isEmpty()){
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
                        if( image.file_id == null ) {
                            AsyncImage(
                                model = sampleImageByteArray,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            AsyncImage(
                                model = try {
                                    if (image.file_id != null) read(context, image.file_id) else sampleImageByteArray
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
                            )

                        }
                    }
                }

                // 요소가 1개나 2개만 있는 마지막 줄 처리
                repeat(3 - scheduleChuck.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}

