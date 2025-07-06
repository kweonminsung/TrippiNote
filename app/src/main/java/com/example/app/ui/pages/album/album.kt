package com.example.app.ui.pages.album


import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

// Albums/
fun getAlbumRoot(context: Context): File = File(context.filesDir, "Albums")

// Albums/depth1/depth2/depth3
fun createAlbumPath(context: Context, depth1: String, depth2: String, depth3: String): File {
    val path = File(getAlbumRoot(context), "$depth1/$depth2/$depth3")
    if (!path.exists()) path.mkdirs()
    return path
}

// 현재 폴더 내용 분리: 폴더와 이미지
fun listFolderContents(folder: File): Pair<List<File>, List<File>> {
    val files = folder.listFiles()?.toList() ?: emptyList()
    val folders = files.filter { it.isDirectory }
    val images = files.filter { it.isFile }
    return folders to images
}

// 현재 위치 기준으로 하위 폴더 생성
fun createSubFolder(context: Context, currentPath: String, folderName: String): File {
    val newFolder = File(getAlbumRoot(context), "$currentPath/$folderName")
    if (!newFolder.exists()) newFolder.mkdirs()
    return newFolder
}

// 사진 파일 저장: uri -> 파일 복사
fun saveImageToAlbum(context: Context, uri: Uri, albumPath: String) {
    val albumDir = File(getAlbumRoot(context), albumPath)
    if (!albumDir.exists()) albumDir.mkdirs()

    val fileName = "IMG_${System.currentTimeMillis()}.jpg"
    val destFile = File(albumDir, fileName)

    context.contentResolver.openInputStream(uri)?.use { input ->
        FileOutputStream(destFile).use { output ->
            input.copyTo(output)
        }
    }
}
