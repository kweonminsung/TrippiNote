package com.example.app.util.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS trip (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT,
                lat REAL,
                lng REAL,
                start_date TEXT,
                end_date TEXT,
                created_at TEXT
            );
        """)
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS region (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                trip_id INT,
                title TEXT,
                lat REAL,
                lng REAL,
                start_date TEXT,
                end_date TEXT,
                created_at TEXT,
                FOREIGN KEY(trip_id) REFERENCES trip(id)
            );
        """)
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS transport (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                region_id INTEGER,
                from_schedule_id INTEGER,
                to_schedule_id INTEGER,
                type TEXT CHECK(type IN ('WALKING', 'BICYCLE', 'CAR', 'BUS', 'TRAIN', 'SUBWAY', 'AIRPLANE')),
                duration TEXT,
                created_at TEXT,
                memo TEXT,
                FOREIGN KEY(region_id) REFERENCES region(id),
                FOREIGN KEY(from_schedule_id) REFERENCES schedule(id),
                FOREIGN KEY(to_schedule_id) REFERENCES schedule(id)
            );
        """)
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS schedule (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                region_id INTEGER,
                place_id TEXT NULL,
                title TEXT,
                memo TEXT,
                lat REAL,
                lng REAL,
                start_date TEXT,
                end_date TEXT,
                created_at TEXT,
                FOREIGN KEY(region_id) REFERENCES region(id)
            );
        """)
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS wishlist (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                content TEXT,
                done BOOLEAN DEFAULT 0
            );
        """)
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS checklist (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                content TEXT,
                done BOOLEAN DEFAULT 0
            );
        """)
        // mock data insert
        db.execSQL("INSERT INTO wishlist (id, content, done) VALUES (1, 'Visit the Eiffel Tower', 1);")
        db.execSQL("INSERT INTO wishlist (id, content, done) VALUES (2, 'See the Northern Lights', 0);")
        db.execSQL("INSERT INTO checklist (id, content, done) VALUES (1, 'Book flight tickets', 1);")
        db.execSQL("INSERT INTO checklist (id, content, done) VALUES (2, 'Reserve hotel room', 0);")
        db.execSQL("INSERT INTO trip (id, title, lat, lng, start_date, end_date, created_at) VALUES (1, 'Trip to Paris', 48.8566, 2.3522, '2024-05-01', '2024-05-10', '2024-04-01T12:00:00Z');")
        db.execSQL("INSERT INTO trip (id, title, lat, lng, start_date, end_date, created_at) VALUES (2, 'Trip to Tokyo', 35.6762, 139.6503, '2024-06-01', '2024-06-10', '2024-05-01T12:00:00Z');")
        db.execSQL("INSERT INTO region (id, trip_id, title, lat, lng, start_date, end_date, created_at) VALUES (1, 1, 'Region of Paris', 48.8566, 2.3522, '2024-05-01', '2024-05-10', '2024-04-01T12:00:00Z');")
        db.execSQL("INSERT INTO region (id, trip_id, title, lat, lng, start_date, end_date, created_at) VALUES (2, 1, 'Region of Versailles', 48.8049, 2.1204, '2024-05-03', '2024-05-04', '2024-04-01T12:00:00Z');")
        db.execSQL("INSERT INTO region (id, trip_id, title, lat, lng, start_date, end_date, created_at) VALUES (3, 2, 'Region of Tokyo', 35.6762, 139.6503, '2024-06-01', '2024-06-10', '2024-05-01T12:00:00Z');")
        db.execSQL("INSERT INTO schedule (id, region_id, title, memo, lat, lng, start_date, end_date, created_at) VALUES (1, 1, 'Visit Louvre Museum', 'Don''t forget to buy tickets in advance', 48.8606, 2.3376, '2024-05-02T10:00:00Z', '2024-05-02T12:00:00Z', '2024-04-01T12:00:00Z');")
        db.execSQL("INSERT INTO schedule (id, region_id, title, memo, lat, lng, start_date, end_date, created_at) VALUES (2, 1, 'Walk along the Seine River', 'Enjoy the view and take photos', 48.8566, 2.3522, NULL, '2024-05-02T16:00:00Z', '2024-04-01T12:00:00Z');")
        db.execSQL("INSERT INTO schedule (id, region_id, title, memo, lat, lng, start_date, end_date, created_at) VALUES (3, 1, 'Dinner at a local bistro', 'Try the escargot and coq au vin', 48.8956, 2.3522, '2024-05-02T19:00:00Z', '2024-05-02T21:00:00Z', '2024-04-01T12:00:00Z');")
        db.execSQL("INSERT INTO schedule (id, region_id, title, memo, lat, lng, start_date, end_date, created_at) VALUES (4, 2, 'Visit Palace of Versailles', 'Check the opening hours', 48.8049, 2.1204, '2024-05-03T09:00:00Z', '2024-05-03T17:00:00Z', '2024-04-01T12:00:00Z');")
        db.execSQL("INSERT INTO schedule (id, region_id, title, memo, lat, lng, start_date, end_date, created_at) VALUES (5, 3, 'Visit Shibuya Crossing', 'Experience the busiest pedestrian crossing', 35.6586, 139.7012, '2024-06-02T15:00:00Z', '2024-06-02T17:00:00Z', '2024-05-01T12:00:00Z');")
        db.execSQL("INSERT INTO transport (id, region_id, from_schedule_id, to_schedule_id, type, duration, created_at, memo) VALUES (1, 1, 1, 2, 'WALKING', '03:00:00', '2024-04-01T12:00:00Z', 'Walk from Louvre to Seine');")
        db.execSQL("INSERT INTO transport (id, region_id, from_schedule_id, to_schedule_id, type, duration, created_at, memo) VALUES (2, 1, 2, 3, 'BUS', '00:30:00', '2024-04-01T12:00:00Z', 'Walk from Seine to bistro');")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS trip")
        db.execSQL("DROP TABLE IF EXISTS transport")
        db.execSQL("DROP TABLE IF EXISTS schedule")
        db.execSQL("DROP TABLE IF EXISTS region")
        db.execSQL("DROP TABLE IF EXISTS wishlist")
        db.execSQL("DROP TABLE IF EXISTS checklist")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "database.db"
        private const val DB_VERSION = 1
    }
}

object DatabaseUtil {
    fun getReadableDb(context: Context): SQLiteDatabase {
        return SQLiteHelper(context).readableDatabase
    }

    fun getWritableDb(context: Context): SQLiteDatabase {
        return SQLiteHelper(context).writableDatabase
    }

    fun execSql(context: Context, sql: String, args: Array<Any?>? = null) {
        val db = getWritableDb(context)
        if (args != null) {
            db.execSQL(sql, args)
        } else {
            db.execSQL(sql)
        }
        db.close()
    }

    fun rawQuery(context: Context, sql: String, args: Array<String>? = null): List<Map<String, Any?>> {
        val db = getReadableDb(context)
        val cursor = db.rawQuery(sql, args)
        val result = mutableListOf<Map<String, Any?>>()
        val columnNames = cursor.columnNames
        while (cursor.moveToNext()) {
            val row = mutableMapOf<String, Any?>()
            for (col in columnNames) {
                val idx = cursor.getColumnIndex(col)
                row[col] = when (cursor.getType(idx)) {
                    Cursor.FIELD_TYPE_INTEGER -> cursor.getLong(idx)
                    Cursor.FIELD_TYPE_FLOAT -> cursor.getDouble(idx)
                    Cursor.FIELD_TYPE_STRING -> cursor.getString(idx)
                    Cursor.FIELD_TYPE_BLOB -> cursor.getBlob(idx)
                    else -> null
                }
            }
            result.add(row)
        }
        cursor.close()
        db.close()
        return result
    }

//    inline fun <T> getFirstRow(context: Context, sql: String, args: Array<String>? = null, rowMapper: (Map<String, Any?>) -> T): T? {
//        val results = rawQuery(context, sql, args)
//        return if (results.isNotEmpty()) rowMapper(results[0]) else null
//    }
//
//    inline fun <T> getAllRows(context: Context, sql: String, args: Array<String>? = null, rowMapper: (Map<String, Any?>) -> T): List<T> {
//        return rawQuery(context, sql, args).map(rowMapper)
//    }
}
