package com.example.app.util

import android.content.Context
import java.io.IOException
import java.util.UUID

object ObjectStorage {
    fun save(context: Context, bytes: ByteArray): String? {
        val uuid = UUID.randomUUID().toString()
        return try {
            context.openFileOutput(uuid, Context.MODE_PRIVATE).use {
                it.write(bytes)
            }
            uuid
        } catch (e: IOException) {
            e.printStackTrace()
            null
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
