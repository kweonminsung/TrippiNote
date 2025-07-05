package com.example.app.util

import android.content.Context
import java.io.IOException

object ObjectStorage {
    fun save(context: Context, uuid: String, bytes: ByteArray): Boolean {
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

    fun read(context: Context, uuid: String): ByteArray? {
        return try {
            context.openFileInput(uuid).use { it.readBytes() }
        } catch (e: IOException) {
            null
        }
    }

    fun delete(context: Context, uuid: String): Boolean {
        return context.deleteFile(uuid)
    }

    fun exists(context: Context, uuid: String): Boolean {
        return context.getFileStreamPath(uuid).exists()
    }
}

