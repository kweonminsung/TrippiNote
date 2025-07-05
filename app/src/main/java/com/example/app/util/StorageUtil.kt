package com.example.app.util

import android.content.Context
import java.io.File
import java.io.IOException

object StorageUtil {
    // StorageUtil에서 key-value, object, textfile 저장소 관련 함수 분리 예정
    // 이 파일에는 StorageUtil의 shell만 남기고, 각 기능별로 별도 파일로 분리 예정

//    fun saveTextToFile(context: Context, fileName: String, text: String): Boolean {
//        return try {
//            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
//                it.write(text.toByteArray())
//            }
//            true
//        } catch (e: IOException) {
//            e.printStackTrace()
//            false
//        }
//    }
//
//    fun readTextFromFile(context: Context, fileName: String): String? {
//        return try {
//            context.openFileInput(fileName).bufferedReader().use { it.readText() }
//        } catch (e: IOException) {
//            e.printStackTrace()
//            null
//        }
//    }

}
