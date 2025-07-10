package com.example.app.util

import android.content.Context
import com.example.app.type.INITIAL_SESSION_DATA
import com.example.app.type.SessionData
import com.google.gson.Gson
import java.io.IOException

object KeyValueStore {
    private const val APP_KV_STORE = "app_kv_store"
    private val gson = Gson()

    fun saveBulk(context: Context, data: SessionData): Boolean {
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

    fun loadBulk(context: Context): SessionData? {
        return try {
            val jsonString = context.openFileInput(APP_KV_STORE).bufferedReader().readText()
            if (jsonString.isBlank()) null else gson.fromJson(jsonString, SessionData::class.java)
        } catch (e: IOException) {
            null
        }
    }

    fun clear(context: Context) {
        context.deleteFile(APP_KV_STORE)
        saveBulk(context, INITIAL_SESSION_DATA)
    }
}
