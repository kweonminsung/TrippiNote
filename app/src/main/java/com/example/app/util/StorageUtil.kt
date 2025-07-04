package com.example.app.util

import android.content.Context
import java.io.File
import java.io.IOException

object StorageUtil {
    private const val APP_KV_STORE = "app_kv_store"

    fun saveTextToFile(context: Context, fileName: String, text: String): Boolean {
        return try {
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                it.write(text.toByteArray())
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun readTextFromFile(context: Context, fileName: String): String? {
        return try {
            context.openFileInput(fileName).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun deleteFile(context: Context, fileName: String): Boolean {
        return context.deleteFile(fileName)
    }

    fun fileExists(context: Context, fileName: String): Boolean {
        val file = File(context.filesDir, fileName)
        return file.exists()
    }

    // 앱 자체의 key-value 정보 저장 (SharedPreferences 대체)
    fun saveKeyValue(context: Context, key: String, value: String): Boolean {
        return try {
            context.openFileOutput(APP_KV_STORE, Context.MODE_PRIVATE or Context.MODE_APPEND).use {
                it.write("$key=$value\n".toByteArray())
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun readKeyValue(context: Context, key: String): String? {
        return try {
            val lines = context.openFileInput(APP_KV_STORE).bufferedReader().readLines()
            lines.lastOrNull { it.startsWith("$key=") }?.substringAfter('=')
        } catch (e: IOException) {
            null
        }
    }

    fun initKeyValueStore(context: Context) {
        context.deleteFile(APP_KV_STORE)
    }

    fun insertMockKeyValueData(context: Context) {
        val mockData = mapOf(
            "user_id" to "test_user",
            "theme" to "dark",
            "language" to "ko",
            "isFirstLaunch" to "false"
        )
        mockData.forEach { (k, v) -> saveKeyValue(context, k, v) }
    }

    // 사진 등 바이너리 오브젝트 저장 (uuid 기반)
    fun saveObject(context: Context, uuid: String, bytes: ByteArray): Boolean {
        return try {
            context.openFileOutput(uuid, Context.MODE_PRIVATE).use {
                it.write(bytes)
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun readObject(context: Context, uuid: String): ByteArray? {
        return try {
            context.openFileInput(uuid).use { it.readBytes() }
        } catch (e: IOException) {
            null
        }
    }
}
