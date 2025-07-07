package com.example.app.ui.pages.album


import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.CustomColors
import com.example.app.util.database.model
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import coil.compose.AsyncImage


fun createAlbumFolder(baseDir: File, trip: model.Trip, region: model.Region, schedule: model.Schedule): File {
    val tripFolder = File(baseDir, trip.title)
    val regionFolder = File(tripFolder, region.title)
    val scheduleFolder = File(regionFolder, schedule.title)

    if (!scheduleFolder.exists()) {
        scheduleFolder.mkdirs()
    }

    return scheduleFolder
}

fun copyInputStreamToFile(inputStream: InputStream, destFile: File) {
    inputStream.use { input ->
        destFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }
}

fun getRandomThumbnailFromFolder(folder: File): File? {
    val imageFiles = folder.listFiles { f -> f.extension in listOf("jpg", "png", "jpeg") } ?: return null
    return imageFiles.randomOrNull()
}


@Composable
fun FolderItem(
    name: String,
    thumbnail: File?,
    onClick: () -> Unit
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