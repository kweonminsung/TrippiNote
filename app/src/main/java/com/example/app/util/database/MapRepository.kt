package com.example.app.util.database

import android.content.Context

object MapRepository {
    fun getTrips(context: Context): List<model.Trip> {
        val dbHelper = SQLiteHelper(context)

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

    fun getAllRegions(context: Context): List<model.Region> {
        val dbHelper = SQLiteHelper(context)

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

    fun getRegions(context: Context, tripId: Int): List<model.Region> {
        val dbHelper = SQLiteHelper(context)

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

    fun getAllSchedules(context: Context): List<model.Schedule> {
        val dbHelper = SQLiteHelper(context)

        val schedules = mutableListOf<model.Schedule>()
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM schedule", null)
            val idIdx = cursor.getColumnIndex("id")
            val typeIdx = cursor.getColumnIndex("type")
            val regionIdIdx = cursor.getColumnIndex("region_id")
            val titleIdx = cursor.getColumnIndex("title")
            val memoIdx = cursor.getColumnIndex("memo")
            val latIdx = cursor.getColumnIndex("lat")
            val lngIdx = cursor.getColumnIndex("lng")
            val startDatetimeIdx = cursor.getColumnIndex("start_datetime")
            val endDatetimeIdx = cursor.getColumnIndex("end_datetime")
            val createdAtIdx = cursor.getColumnIndex("created_at")
            while (cursor.moveToNext()) {
                schedules.add(
                    model.Schedule(
                        id = cursor.getInt(idIdx),
                        type = model.ScheduleType.valueOf(cursor.getString(typeIdx)),
                        region_id = cursor.getInt(regionIdIdx),
                        title = cursor.getString(titleIdx),
                        memo = cursor.getString(memoIdx),
                        lat = cursor.getDouble(latIdx),
                        lng = cursor.getDouble(lngIdx),
                        start_datetime = cursor.getString(startDatetimeIdx),
                        end_datetime = cursor.getString(endDatetimeIdx),
                        created_at = cursor.getString(createdAtIdx)
                    )
                )
            }
            cursor.close()
        }
        return schedules
    }

    fun getSchedules(context: Context, regionId: Int): List<model.Schedule> {
        val dbHelper = SQLiteHelper(context)

        val schedules = mutableListOf<model.Schedule>()
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM schedule WHERE region_id = ?", arrayOf(regionId.toString()))
            val idIdx = cursor.getColumnIndex("id")
            val typeIdx = cursor.getColumnIndex("type")
            val titleIdx = cursor.getColumnIndex("title")
            val memoIdx = cursor.getColumnIndex("memo")
            val latIdx = cursor.getColumnIndex("lat")
            val lngIdx = cursor.getColumnIndex("lng")
            val startDatetimeIdx = cursor.getColumnIndex("start_datetime")
            val endDatetimeIdx = cursor.getColumnIndex("end_datetime")
            val createdAtIdx = cursor.getColumnIndex("created_at")
            while (cursor.moveToNext()) {
                schedules.add(
                    model.Schedule(
                        id = cursor.getInt(idIdx),
                        type = model.ScheduleType.valueOf(cursor.getString(typeIdx)),
                        region_id = regionId,
                        title = cursor.getString(titleIdx),
                        memo = cursor.getString(memoIdx),
                        lat = cursor.getDouble(latIdx),
                        lng = cursor.getDouble(lngIdx),
                        start_datetime = cursor.getString(startDatetimeIdx),
                        end_datetime = cursor.getString(endDatetimeIdx),
                        created_at = cursor.getString(createdAtIdx)
                    )
                )
            }
            cursor.close()
        }
        return schedules
    }

    fun getScheduleById(context: Context, scheduleId: Int): model.Schedule? {
        val dbHelper = SQLiteHelper(context)

        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM schedule WHERE id = ?", arrayOf(scheduleId.toString()))
            if (cursor.moveToFirst()) {
                val idIdx = cursor.getColumnIndex("id")
                val typeIdx = cursor.getColumnIndex("type")
                val regionIdIdx = cursor.getColumnIndex("region_id")
                val titleIdx = cursor.getColumnIndex("title")
                val memoIdx = cursor.getColumnIndex("memo")
                val latIdx = cursor.getColumnIndex("lat")
                val lngIdx = cursor.getColumnIndex("lng")
                val startDatetimeIdx = cursor.getColumnIndex("start_datetime")
                val endDatetimeIdx = cursor.getColumnIndex("end_datetime")
                val createdAtIdx = cursor.getColumnIndex("created_at")

                return model.Schedule(
                    id = cursor.getInt(idIdx),
                    type = model.ScheduleType.valueOf(cursor.getString(typeIdx)),
                    region_id = cursor.getInt(regionIdIdx),
                    title = cursor.getString(titleIdx),
                    memo = cursor.getString(memoIdx),
                    lat = cursor.getDouble(latIdx),
                    lng = cursor.getDouble(lngIdx),
                    start_datetime = cursor.getString(startDatetimeIdx),
                    end_datetime = cursor.getString(endDatetimeIdx),
                    created_at = cursor.getString(createdAtIdx)
                )
            }
            cursor.close()
        }
        return null
    }

    fun getAllTransports(context: Context): List<model.Transport> {
        val dbHelper = SQLiteHelper(context)

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

    fun getTransports(context: Context, regionId: Int): List<model.Transport> {
        val dbHelper = SQLiteHelper(context)

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

    fun getTransportByFromTo(
        context: Context,
        regionId: Int,
        fromScheduleId: Int,
        toScheduleId: Int
    ): model.Transport? {
        val dbHelper = SQLiteHelper(context)

        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery(
                "SELECT * FROM transport WHERE region_id = ? AND from_schedule_id = ? AND to_schedule_id = ?",
                arrayOf(regionId.toString(), fromScheduleId.toString(), toScheduleId.toString())
            )
            if (cursor.moveToFirst()) {
                val idIdx = cursor.getColumnIndex("id")
                val typeIdx = cursor.getColumnIndex("type")
                val durationIdx = cursor.getColumnIndex("duration")
                val createdAtIdx = cursor.getColumnIndex("created_at")
                val memoIdx = cursor.getColumnIndex("memo")

                return model.Transport(
                    id = cursor.getInt(idIdx),
                    region_id = regionId,
                    from_schedule_id = fromScheduleId,
                    to_schedule_id = toScheduleId,
                    type = model.TransportType.valueOf(cursor.getString(typeIdx)),
                    duration = cursor.getString(durationIdx),
                    created_at = cursor.getString(createdAtIdx),
                    memo = cursor.getString(memoIdx)
                )
            }
            cursor.close()
        }
        return null
    }

    fun createTrip(context: Context, trip: model.Trip): Long {
        val dbHelper = SQLiteHelper(context)
        dbHelper.writableDatabase.use { db ->
            val values = android.content.ContentValues().apply {
                put("title", trip.title)
                put("lat", trip.lat)
                put("lng", trip.lng)
                put("start_date", trip.start_date)
                put("end_date", trip.end_date)
                put("created_at", trip.created_at)
            }
            return db.insert("trip", null, values)
        }
    }

    fun updateTrip(context: Context, trip: model.Trip) {
        val dbHelper = SQLiteHelper(context)
        dbHelper.writableDatabase.use { db ->
            val values = android.content.ContentValues().apply {
                put("title", trip.title)
                put("lat", trip.lat)
                put("lng", trip.lng)
                put("start_date", trip.start_date)
                put("end_date", trip.end_date)
                put("created_at", trip.created_at)
            }
            db.update("trip", values, "id = ?", arrayOf(trip.id.toString()))
        }
    }

    fun deleteTrip(context: Context, tripId: Int) {
        val dbHelper = SQLiteHelper(context)
        dbHelper.writableDatabase.use { db ->
            db.delete("trip", "id = ?", arrayOf(tripId.toString()))
        }
    }

    fun createRegion(context: Context, region: model.Region): Long {
        val dbHelper = SQLiteHelper(context)
        dbHelper.writableDatabase.use { db ->
            val values = android.content.ContentValues().apply {
                put("trip_id", region.trip_id)
                put("title", region.title)
                put("lat", region.lat)
                put("lng", region.lng)
                put("start_date", region.start_date)
                put("end_date", region.end_date)
                put("created_at", region.created_at)
            }
            return db.insert("region", null, values)
        }
    }

    fun updateRegion(context: Context, region: model.Region) {
        val dbHelper = SQLiteHelper(context)
        dbHelper.writableDatabase.use { db ->
            val values = android.content.ContentValues().apply {
                put("trip_id", region.trip_id)
                put("title", region.title)
                put("lat", region.lat)
                put("lng", region.lng)
                put("start_date", region.start_date)
                put("end_date", region.end_date)
                put("created_at", region.created_at)
            }
            db.update("region", values, "id = ?", arrayOf(region.id.toString()))
        }
    }

    fun deleteRegion(context: Context, regionId: Int) {
        val dbHelper = SQLiteHelper(context)
        dbHelper.writableDatabase.use { db ->
            db.delete("region", "id = ?", arrayOf(regionId.toString()))
        }
    }

    fun createSchedule(context: Context, schedule: model.Schedule): Long {
        val dbHelper = SQLiteHelper(context)
        dbHelper.writableDatabase.use { db ->
            val values = android.content.ContentValues().apply {
                put("type", schedule.type.name)
                put("region_id", schedule.region_id)
                put("title", schedule.title)
                put("memo", schedule.memo)
                put("lat", schedule.lat)
                put("lng", schedule.lng)
                put("start_datetime", schedule.start_datetime)
                put("end_datetime", schedule.end_datetime)
                put("created_at", schedule.created_at)
            }
            return db.insert("schedule", null, values)
        }
    }

fun updateSchedule(context: Context, schedule: model.Schedule) {
        val dbHelper = SQLiteHelper(context)
        dbHelper.writableDatabase.use { db ->
            val values = android.content.ContentValues().apply {
                put("type", schedule.type.name)
                put("region_id", schedule.region_id)
                put("title", schedule.title)
                put("memo", schedule.memo)
                put("lat", schedule.lat)
                put("lng", schedule.lng)
                put("start_datetime", schedule.start_datetime)
                put("end_datetime", schedule.end_datetime)
                put("created_at", schedule.created_at)
            }
            db.update("schedule", values, "id = ?", arrayOf(schedule.id.toString()))
        }
    }

fun deleteSchedule(context: Context, scheduleId: Int) {
        val dbHelper = SQLiteHelper(context)
        dbHelper.writableDatabase.use { db ->
            db.delete("schedule", "id = ?", arrayOf(scheduleId.toString()))
        }
    }

    fun createTransport(context: Context, transport: model.Transport): Long {
        val dbHelper = SQLiteHelper(context)
        dbHelper.writableDatabase.use { db ->
            val values = android.content.ContentValues().apply {
                put("region_id", transport.region_id)
                put("from_schedule_id", transport.from_schedule_id)
                put("to_schedule_id", transport.to_schedule_id)
                put("type", transport.type.name)
                put("duration", transport.duration)
                put("created_at", transport.created_at)
                put("memo", transport.memo)
            }
            return db.insert("transport", null, values)
        }
    }

    fun updateTransport(context: Context, transport: model.Transport) {
        val dbHelper = SQLiteHelper(context)
        dbHelper.writableDatabase.use { db ->
            val values = android.content.ContentValues().apply {
                put("region_id", transport.region_id)
                put("from_schedule_id", transport.from_schedule_id)
                put("to_schedule_id", transport.to_schedule_id)
                put("type", transport.type.name)
                put("duration", transport.duration)
                put("created_at", transport.created_at)
                put("memo", transport.memo)
            }
            db.update("transport", values, "id = ?", arrayOf(transport.id.toString()))
        }
    }

    fun deleteTransport(context: Context, transportId: Int) {
        val dbHelper = SQLiteHelper(context)
        dbHelper.writableDatabase.use { db ->
            db.delete("transport", "id = ?", arrayOf(transportId.toString()))
        }
    }
}