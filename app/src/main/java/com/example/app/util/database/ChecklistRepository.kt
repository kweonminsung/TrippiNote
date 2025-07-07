package com.example.app.util.database

import android.content.Context

object ChecklistRepository {
    fun getChecklistItems(context: Context): List<model.ChecklistItem> {
        val dbHelper = SQLiteHelper(context)

        val checklistItems = mutableListOf<model.ChecklistItem>()
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM checklist", null)
            val doneIdx = cursor.getColumnIndex("done")
            val contentIdx = cursor.getColumnIndex("content")
            while (cursor.moveToNext()) {
                checklistItems.add(
                    model.ChecklistItem(
                        done = cursor.getInt(doneIdx) > 0,
                        content = cursor.getString(contentIdx)
                    )
                )
            }
            cursor.close()
        }
        return checklistItems
    }
}