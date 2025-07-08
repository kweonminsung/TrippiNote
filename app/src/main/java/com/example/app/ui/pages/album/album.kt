package com.example.app.ui.pages.album


import android.content.Context
import android.net.Uri
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.CustomColors
import com.example.app.util.database.model
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import coil.compose.AsyncImage
import com.example.app.R.drawable.sample_image
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



fun UploadImage(
    context: Context,
    file: File,
    schdule: model.Schedule
){
    val file_id = save(context, file.readBytes()).toString()
    createScheduleImage(context, schdule.id,file_id)
}

@Composable
fun FolderItem( // 앨범 폴더 썸네일 + 이름 + 클릭 이벤트
    name: String,
    thumbnail: ImageResult?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(CustomColors.LightGray)
            )

        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(name, fontSize = 18.sp, modifier = Modifier.align(Alignment.CenterVertically))
    }
}

@Composable
fun ImageItem(
    image: ByteArray?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f) // 정사각형
            .clip(RoundedCornerShape(6.dp))
            .background(CustomColors.LightGray)
            .clickable(onClick = onClick)
    ) {
        if (image != null) {
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = "Placeholder",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}



@Composable
fun TripFolderGridColumn(
    context: Context,
    onTripFolderClick: (model.Trip) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val trip_folders = getAllTrips(context)
        trip_folders.chunked(2).forEach { rowFolders ->
            if (trip_folders.isEmpty()) {
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
                return@Column
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowFolders.forEach { trip_folders ->
                        FolderItem(
                            name = trip_folders.title,
                            thumbnail = getRandomTripImages(context).random(),
                            onClick = { onTripFolderClick(trip_folders) },
                            modifier = Modifier
                                .weight(1f)
                        )
                    }

                    // 만약 마지막 줄에 폴더가 1개라면 나머지 공간 채우기
                    if (rowFolders.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp)) // Row 간 간격
            }
        }
    }
}

@Composable
fun RegionFolderGridColumn(
    context: Context,
    trip: model.Trip,
    onRegionFolderClick: (model.Region) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val region_folders = getRegions(context, trip.id)
        region_folders.chunked(2).forEach { rowFolders ->
            if (region_folders.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "지역 기록이 없습니다",
                        color = CustomColors.DarkGray,
                        fontSize = 18.sp
                    )
                }
                return@Column
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowFolders.forEach { region_folders ->
                        FolderItem(
                            name = region_folders.title,
                            thumbnail = getRandomTripImages(context).random(),
                            onClick = { onRegionFolderClick(region_folders) },
                            modifier = Modifier
                                .weight(1f)
                        )
                    }

                    // 만약 마지막 줄에 폴더가 1개라면 나머지 공간 채우기
                    if (rowFolders.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp)) // Row 간 간격
            }
        }
    }
}

@Composable
fun ScheduleFolderGridColumn(
    context: Context,
    region: model.Region,
    onScheduleFolderClick: (model.Schedule) -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val schedule_folders = getSchedules(context, region.id)
        schedule_folders.chunked(2).forEach { rowFolders ->
            if( schedule_folders.isEmpty() ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "일정 기록이 없습니다",
                        color = CustomColors.DarkGray,
                        fontSize = 18.sp
                    )
                }
                return@Column
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowFolders.forEach { schedule_folders ->
                        FolderItem(
                            name = schedule_folders.title,
                            thumbnail = getRandomTripImages(context).random(),
                            onClick = { onScheduleFolderClick(schedule_folders) },
                            modifier = Modifier
                                .weight(1f)
                        )
                    }

                    // 만약 마지막 줄에 폴더가 1개라면 나머지 공간 채우기
                    if (rowFolders.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp)) // Row 간 간격
            }
        }
    }
}

@Composable
fun ScheduleImageGrid(
    context: Context,
    schedule: model.Schedule,
    onBack: (ImageResult) -> Unit
) {
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

        schedule_images.chunked(3).forEach { rowFolders ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowFolders.forEach { schedule_images ->
                    ImageItem(
                        image = read(context, schedule_images.file_id),
                        onClick = { onBack(schedule_images) },
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f) // 정사각형
                    )
                }

                // 만약 마지막 줄에 사진이 1, 2개라면 나머지 공간 채우기
                if (rowFolders.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.weight(1f))
                } else if (rowFolders.size < 3) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(12.dp)) // Row 간 간격
        }
    }
}

