package com.example.app.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object KeyValueStore {
    private const val APP_KV_STORE = "app_kv_store"
    private val gson = Gson()

    fun save(context: Context, key: String, value: Any): Boolean {
        val all = getAll(context).toMutableMap()
        all[key] = value
        return saveBulk(context, all)
    }

    fun saveBulk(context: Context, data: Map<String, Any>): Boolean {
        return try {
            val jsonString = gson.toJson(data)
            context.openFileOutput(APP_KV_STORE, Context.MODE_PRIVATE).use {
                it.write(jsonString.toByteArray())
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun readRaw(context: Context, key: String): Any? {
        return getAll(context)[key]
    }

    fun <T> read(context: Context, key: String, clazz: Class<T>): T? {
        val value = getAll(context)[key] ?: return null
        return try {
            gson.fromJson(gson.toJson(value), clazz)
        } catch (e: Exception) {
            null
        }
    }

    fun getAll(context: Context): Map<String, Any> {
        return try {
            val jsonString = context.openFileInput(APP_KV_STORE).bufferedReader().readText()
            if (jsonString.isBlank()) emptyMap() else gson.fromJson(jsonString, object : TypeToken<Map<String, Any>>(){}.type)
        } catch (e: IOException) {
            emptyMap()
        }
    }

    fun clear(context: Context) {
        context.deleteFile(APP_KV_STORE)
    }

    fun insertMockData(context: Context) {
        val mockData = mapOf(
            "user_id" to "test_user",
            "theme" to "dark",
            "language" to "ko",
            "isFirstLaunch" to false,
            "numbers" to listOf(1,2,3),
            "profile" to mapOf("name" to "홍길동", "age" to 25)
        )
        saveBulk(context, mockData)
    }
}
