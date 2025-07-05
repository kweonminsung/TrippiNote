package com.example.app.ui.pages.album

import android.content.Context
import java.io.File

// 앨범 루트 디렉터리
fun getAlbumRoot(context: Context): File {
    return File(context.filesDir, "Albums")
}

// 현재 경로 기준 폴더 가져오기
fun getFolder(context: Context, currentPath: String): File {
    return File(getAlbumRoot(context), currentPath)
}

// 현재 폴더 안의 파일/폴더 리스트
fun listFolderContents(folder: File): Pair<List<File>, List<File>> {
    val files = folder.listFiles()?.toList() ?: emptyList()
    val folders = files.filter { it.isDirectory }
    val images = files.filter { it.isFile }
    return folders to images
}

// 하위 폴더 생성
fun createSubFolder(context: Context, currentPath: String, folderName: String): File {
    val newFolder = File(getAlbumRoot(context), "$currentPath/$folderName")
    if (!newFolder.exists()) newFolder.mkdirs()
    return newFolder
}
