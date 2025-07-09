package com.example.app.util.database

import android.content.Context

object ChecklistRepository {
    fun getAllChecklists(context: Context): List<model.Checklist> {
        val db = DatabaseUtil.getReadableDb(context)

        val checklists = mutableListOf<model.Checklist>()
        db.use { db ->
            val cursor = db.rawQuery("SELECT * FROM checklist", null)
            val idIdx = cursor.getColumnIndex("id")
            val doneIdx = cursor.getColumnIndex("done")
            val contentIdx = cursor.getColumnIndex("content")
            while (cursor.moveToNext()) {
                checklists.add(
                    model.Checklist(
                        id = cursor.getInt(idIdx),
                        done = cursor.getInt(doneIdx) > 0,
                        content = cursor.getString(contentIdx)
                    )
                )
            }
            cursor.close()
        }
        return checklists
    }

    fun createChecklist(context: Context, item: model.Checklist) {
        val db = DatabaseUtil.getWritableDb(context)
        db.use { db ->
            db.execSQL(
                "INSERT INTO checklist (done, content) VALUES (?, ?)",
                arrayOf(if (item.done) 1 else 0, item.content)
            )
        }
    }

    fun updateChecklist(context: Context, item: model.Checklist) {
        val db = DatabaseUtil.getWritableDb(context)
        db.use { db ->
            db.execSQL(
                "UPDATE checklist SET done = ?, content = ? WHERE id = ?",
                arrayOf(if (item.done) 1 else 0, item.content, item.id)
            )
        }
    }

    fun deleteChecklist(context: Context, item: model.Checklist) {
        val db = DatabaseUtil.getWritableDb(context)
        db.use { db ->
            db.execSQL("DELETE FROM checklist WHERE id = ?", arrayOf(item.id))
        }
    }
}