package com.example.app.util.database

import android.content.Context

object WishlistRepository {
    fun getAllWishlists(context: Context): List<model.Wishlist> {
        val dbHelper = SQLiteHelper(context)
        
        val wishlists = mutableListOf<model.Wishlist>()
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM wishlist", null)
            val doneIdx = cursor.getColumnIndex("done")
            val contentIdx = cursor.getColumnIndex("content")
            while (cursor.moveToNext()) {
                wishlists.add(
                    model.Wishlist(
                        done = cursor.getInt(doneIdx) > 0,
                        content = cursor.getString(contentIdx)
                    )
                )
            }
            cursor.close()
        }
        return wishlists
    }
}