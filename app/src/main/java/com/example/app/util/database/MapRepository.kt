package com.example.app.util.database

object MapRepository {
    fun getTrips(dbHelper: SQLiteHelper): List<model.Trip> {
        val trips = mutableListOf<model.Trip>()
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM trip", null)
            val idIdx = cursor.getColumnIndex("id")
            val titleIdx = cursor.getColumnIndex("title")
            val latIdx = cursor.getColumnIndex("lat")
            val lngIdx = cursor.getColumnIndex("lng")
            val startDateIdx = cursor.getColumnIndex("start_date")
            val endDateIdx = cursor.getColumnIndex("end_date")
            val createdAtIdx = cursor.getColumnIndex("created_at")
            while (cursor.moveToNext()) {
                trips.add(
                    model.Trip(
                        id = cursor.getInt(idIdx),
                        title = cursor.getString(titleIdx),
                        lat = cursor.getDouble(latIdx),
                        lng = cursor.getDouble(lngIdx),
                        start_date = cursor.getString(startDateIdx),
                        end_date = cursor.getString(endDateIdx),
                        created_at = cursor.getString(createdAtIdx)
                    )
                )
            }
            cursor.close()
        }
        return trips
    }

    fun getAllRegions(dbHelper: SQLiteHelper): List<model.Region> {
        val regions = mutableListOf<model.Region>()
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM region", null)
            val idIdx = cursor.getColumnIndex("id")
            val tripIdIdx = cursor.getColumnIndex("trip_id")
            val titleIdx = cursor.getColumnIndex("title")
            val latIdx = cursor.getColumnIndex("lat")
            val lngIdx = cursor.getColumnIndex("lng")
            val startDateIdx = cursor.getColumnIndex("start_date")
            val endDateIdx = cursor.getColumnIndex("end_date")
            val createdAtIdx = cursor.getColumnIndex("created_at")
            while (cursor.moveToNext()) {
                regions.add(
                    model.Region(
                        id = cursor.getInt(idIdx),
                        trip_id = cursor.getInt(tripIdIdx),
                        title = cursor.getString(titleIdx),
                        lat = cursor.getDouble(latIdx),
                        lng = cursor.getDouble(lngIdx),
                        start_date = cursor.getString(startDateIdx),
                        end_date = cursor.getString(endDateIdx),
                        created_at = cursor.getString(createdAtIdx)
                    )
                )
            }
            cursor.close()
        }
        return regions
    }

    fun getRegions(dbHelper: SQLiteHelper, tripId: Int): List<model.Region> {
        val regions = mutableListOf<model.Region>()
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM region WHERE trip_id = ?", arrayOf(tripId.toString()))
            val idIdx = cursor.getColumnIndex("id")
            val titleIdx = cursor.getColumnIndex("title")
            val latIdx = cursor.getColumnIndex("lat")
            val lngIdx = cursor.getColumnIndex("lng")
            val startDateIdx = cursor.getColumnIndex("start_date")
            val endDateIdx = cursor.getColumnIndex("end_date")
            val createdAtIdx = cursor.getColumnIndex("created_at")
            while (cursor.moveToNext()) {
                regions.add(
                    model.Region(
                        id = cursor.getInt(idIdx),
                        trip_id = tripId,
                        title = cursor.getString(titleIdx),
                        lat = cursor.getDouble(latIdx),
                        lng = cursor.getDouble(lngIdx),
                        start_date = cursor.getString(startDateIdx),
                        end_date = cursor.getString(endDateIdx),
                        created_at = cursor.getString(createdAtIdx)
                    )
                )
            }
            cursor.close()
        }
        return regions
    }

    fun getAllSchedules(dbHelper: SQLiteHelper): List<model.Schedule> {
        val schedules = mutableListOf<model.Schedule>()
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM schedule", null)
            val idIdx = cursor.getColumnIndex("id")
            val regionIdIdx = cursor.getColumnIndex("region_id")
            val titleIdx = cursor.getColumnIndex("title")
            val memoIdx = cursor.getColumnIndex("memo")
            val latIdx = cursor.getColumnIndex("lat")
            val lngIdx = cursor.getColumnIndex("lng")
            val startDateIdx = cursor.getColumnIndex("start_date")
            val endDateIdx = cursor.getColumnIndex("end_date")
            val createdAtIdx = cursor.getColumnIndex("created_at")
            while (cursor.moveToNext()) {
                schedules.add(
                    model.Schedule(
                        id = cursor.getInt(idIdx),
                        region_id = cursor.getInt(regionIdIdx),
                        title = cursor.getString(titleIdx),
                        memo = cursor.getString(memoIdx),
                        lat = cursor.getDouble(latIdx),
                        lng = cursor.getDouble(lngIdx),
                        start_date = cursor.getString(startDateIdx),
                        end_date = cursor.getString(endDateIdx),
                        created_at = cursor.getString(createdAtIdx)
                    )
                )
            }
            cursor.close()
        }
        return schedules
    }

    fun getSchedules(dbHelper: SQLiteHelper, regionId: Int): List<model.Schedule> {
        val schedules = mutableListOf<model.Schedule>()
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM schedule WHERE region_id = ?", arrayOf(regionId.toString()))
            val idIdx = cursor.getColumnIndex("id")
            val titleIdx = cursor.getColumnIndex("title")
            val memoIdx = cursor.getColumnIndex("memo")
            val latIdx = cursor.getColumnIndex("lat")
            val lngIdx = cursor.getColumnIndex("lng")
            val startDateIdx = cursor.getColumnIndex("start_date")
            val endDateIdx = cursor.getColumnIndex("end_date")
            val createdAtIdx = cursor.getColumnIndex("created_at")
            while (cursor.moveToNext()) {
                schedules.add(
                    model.Schedule(
                        id = cursor.getInt(idIdx),
                        region_id = regionId,
                        title = cursor.getString(titleIdx),
                        memo = cursor.getString(memoIdx),
                        lat = cursor.getDouble(latIdx),
                        lng = cursor.getDouble(lngIdx),
                        start_date = cursor.getString(startDateIdx),
                        end_date = cursor.getString(endDateIdx),
                        created_at = cursor.getString(createdAtIdx)
                    )
                )
            }
            cursor.close()
        }
        return schedules
    }

    fun getScheduleById(dbHelper: SQLiteHelper, scheduleId: Int): model.Schedule? {
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM schedule WHERE id = ?", arrayOf(scheduleId.toString()))
            if (cursor.moveToFirst()) {
                val idIdx = cursor.getColumnIndex("id")
                val regionIdIdx = cursor.getColumnIndex("region_id")
                val titleIdx = cursor.getColumnIndex("title")
                val memoIdx = cursor.getColumnIndex("memo")
                val latIdx = cursor.getColumnIndex("lat")
                val lngIdx = cursor.getColumnIndex("lng")
                val startDateIdx = cursor.getColumnIndex("start_date")
                val endDateIdx = cursor.getColumnIndex("end_date")
                val createdAtIdx = cursor.getColumnIndex("created_at")

                return model.Schedule(
                    id = cursor.getInt(idIdx),
                    region_id = cursor.getInt(regionIdIdx),
                    title = cursor.getString(titleIdx),
                    memo = cursor.getString(memoIdx),
                    lat = cursor.getDouble(latIdx),
                    lng = cursor.getDouble(lngIdx),
                    start_date = cursor.getString(startDateIdx),
                    end_date = cursor.getString(endDateIdx),
                    created_at = cursor.getString(createdAtIdx)
                )
            }
            cursor.close()
        }
        return null
    }

    fun getAllTransports(dbHelper: SQLiteHelper): List<model.Transport> {
        val transports = mutableListOf<model.Transport>()
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM transport", null)
            val idIdx = cursor.getColumnIndex("id")
            val regionIdIdx = cursor.getColumnIndex("region_id")
            val fromScheduleIdIdx = cursor.getColumnIndex("from_schedule_id")
            val toScheduleIdIdx = cursor.getColumnIndex("to_schedule_id")
            val typeIdx = cursor.getColumnIndex("type")
            val durationIdx = cursor.getColumnIndex("duration")
            val createdAtIdx = cursor.getColumnIndex("created_at")
            val memoIdx = cursor.getColumnIndex("memo")
            while (cursor.moveToNext()) {
                transports.add(
                    model.Transport(
                        id = cursor.getInt(idIdx),
                        region_id = cursor.getInt(regionIdIdx),
                        from_schedule_id = cursor.getInt(fromScheduleIdIdx),
                        to_schedule_id = cursor.getInt(toScheduleIdIdx),
                        type = model.TransportType.valueOf(cursor.getString(typeIdx)),
                        duration = cursor.getString(durationIdx),
                        created_at = cursor.getString(createdAtIdx),
                        memo = cursor.getString(memoIdx)
                    )
                )
            }
            cursor.close()
        }
        return transports
    }

    fun getTransports(dbHelper: SQLiteHelper, regionId: Int): List<model.Transport> {
        val transports = mutableListOf<model.Transport>()
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM transport WHERE region_id = ?", arrayOf(regionId.toString()))
            val idIdx = cursor.getColumnIndex("id")
            val fromScheduleIdIdx = cursor.getColumnIndex("from_schedule_id")
            val toScheduleIdIdx = cursor.getColumnIndex("to_schedule_id")
            val typeIdx = cursor.getColumnIndex("type")
            val durationIdx = cursor.getColumnIndex("duration")
            val createdAtIdx = cursor.getColumnIndex("created_at")
            val memoIdx = cursor.getColumnIndex("memo")
            while (cursor.moveToNext()) {
                transports.add(
                    model.Transport(
                        id = cursor.getInt(idIdx),
                        region_id = regionId,
                        from_schedule_id = cursor.getInt(fromScheduleIdIdx),
                        to_schedule_id = cursor.getInt(toScheduleIdIdx),
                        type = model.TransportType.valueOf(cursor.getString(typeIdx)),
                        duration = cursor.getString(durationIdx),
                        created_at = cursor.getString(createdAtIdx),
                        memo = cursor.getString(memoIdx)
                    )
                )
            }
            cursor.close()
        }
        return transports
    }
}