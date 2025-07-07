package com.example.app.util.database

object WishlistRepository {
    fun getAllWishlists(dbHelper: SQLiteHelper): List<model.Wishlist> {
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