package com.example.app.ui.pages.album


import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File
import java.io.FileOutputStream

@Composable
//fun createAlbumFolder(baseDir: File, trip: Trip, region: Region, schedule: Schedule): File {
//    val tripFolder = File(baseDir, trip.name)
//    val regionFolder = File(tripFolder, region.name)
//    val scheduleFolder = File(regionFolder, schedule.name)
//
//    if (!scheduleFolder.exists()) {
//        scheduleFolder.mkdirs()
//    }
//
//    return scheduleFolder
//}
//
//@Composable
//fun copyInputStreamToFile(inputStream: InputStream, destFile: File) {
//    inputStream.use { input ->
//        destFile.outputStream().use { output ->
//            input.copyTo(output)
//        }
//    }
//}
//
//fun getRandomThumbnailFromFolder(folder: File): File? {
//    val imageFiles = folder.listFiles { f -> f.extension in listOf("jpg", "png", "jpeg") } ?: return null
//    return imageFiles.randomOrNull()
//}
//
//
//@Composable
//fun FolderItem(
//    name: String,
//    thumbnail: File?,
//    onClick: () -> Unit
//) {
////    Row (
////        modifier = Modifier
////            .fillMaxWidth()
////            .clickable(onClick = onClick)
////            .padding(8.dp)
////    ) {
////        if (thumbnail != null) {
////            AsyncImage(
////                model = thumbnail,
////                contentDescription = null,
////                modifier = Modifier
////                    .size(60.dp)
////                    .clip(RoundedCornerShape(4.dp))
////            )
////        } else {
////            Box(
////                modifier = Modifier
////                    .size(60.dp)
////                    .background(Color.LightGray)
////            )
////        }
////
////        Spacer(modifier = Modifier.width(12.dp))
////
////        Text(name, fontSize = 18.sp, modifier = Modifier.align(Alignment.CenterVertically))
//    }
//}