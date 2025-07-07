package com.example.app.util.database

import android.content.Context

object WishlistRepository {
    fun getAllWishlists(context: Context): List<model.Wishlist> {
        val dbHelper = SQLiteHelper(context)

        val wishlists = mutableListOf<model.Wishlist>()
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM wishlist", null)
            val idIdx = cursor.getColumnIndex("id")
            val doneIdx = cursor.getColumnIndex("done")
            val contentIdx = cursor.getColumnIndex("content")
            while (cursor.moveToNext()) {
                wishlists.add(
                    model.Wishlist(
                        id = cursor.getInt(idIdx),
                        done = cursor.getInt(doneIdx) > 0,
                        content = cursor.getString(contentIdx)
                    )
                )
            }
            cursor.close()
        }
        return wishlists
    }

    fun createWishlist(context: Context, wishlist: model.Wishlist) {
        val dbHelper = SQLiteHelper(context)
        dbHelper.writableDatabase.use { db ->
            db.execSQL(
                "INSERT INTO wishlist (done, content) VALUES (?, ?)",
                arrayOf(if (wishlist.done) 1 else 0, wishlist.content)
            )
        }
    }

    fun updateWishlist(context: Context, wishlist: model.Wishlist) {
        val dbHelper = SQLiteHelper(context)
        dbHelper.writableDatabase.use { db ->
            db.execSQL(
                "UPDATE wishlist SET done = ?, content = ? WHERE id = ?",
                arrayOf(if (wishlist.done) 1 else 0, wishlist.content, wishlist.id)
            )
        }
    }

    fun deleteWishlist(context: Context, wishlistId: Int) {
        val dbHelper = SQLiteHelper(context)
        dbHelper.writableDatabase.use { db ->
            db.execSQL("DELETE FROM wishlist WHERE id = ?", arrayOf(wishlistId))
        }
    }
}