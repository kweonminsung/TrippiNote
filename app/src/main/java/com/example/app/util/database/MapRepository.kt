package com.example.app.util.database

import android.content.Context
import com.example.app.ui.components.map.MapPinType
import com.example.app.ui.pages.map.MapSearchResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    fun getTripById(context: Context, tripId: Int): model.Trip? {
        val dbHelper = SQLiteHelper(context)

        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM trip WHERE id = ?", arrayOf(tripId.toString()))
            if (cursor.moveToFirst()) {
                val idIdx = cursor.getColumnIndex("id")
                val titleIdx = cursor.getColumnIndex("title")
                val latIdx = cursor.getColumnIndex("lat")
                val lngIdx = cursor.getColumnIndex("lng")
                val startDateIdx = cursor.getColumnIndex("start_date")
                val endDateIdx = cursor.getColumnIndex("end_date")
                val createdAtIdx = cursor.getColumnIndex("created_at")

                return model.Trip(
                    id = cursor.getInt(idIdx),
                    title = cursor.getString(titleIdx),
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

    fun getPlannedTrip(context: Context): model.Trip? {
        val dbHelper = SQLiteHelper(context)

        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val todayString = today.format(formatter)

        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery(
                """
                SELECT * FROM trip 
                WHERE start_date >= ? 
                ORDER BY start_date ASC 
                LIMIT 1
                """.trimIndent(),
                arrayOf(todayString)
            )
            if (cursor.moveToFirst()) {
                val idIdx = cursor.getColumnIndex("id")
                val titleIdx = cursor.getColumnIndex("title")
                val latIdx = cursor.getColumnIndex("lat")
                val lngIdx = cursor.getColumnIndex("lng")
                val startDateIdx = cursor.getColumnIndex("start_date")
                val endDateIdx = cursor.getColumnIndex("end_date")
                val createdAtIdx = cursor.getColumnIndex("created_at")

                return model.Trip(
                    id = cursor.getInt(idIdx),
                    title = cursor.getString(titleIdx),
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

    fun getRegionById(context: Context, regionId: Int): model.Region? {
        val dbHelper = SQLiteHelper(context)

        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT * FROM region WHERE id = ?", arrayOf(regionId.toString()))
            if (cursor.moveToFirst()) {
                val idIdx = cursor.getColumnIndex("id")
                val tripIdIdx = cursor.getColumnIndex("trip_id")
                val titleIdx = cursor.getColumnIndex("title")
                val latIdx = cursor.getColumnIndex("lat")
                val lngIdx = cursor.getColumnIndex("lng")
                val startDateIdx = cursor.getColumnIndex("start_date")
                val endDateIdx = cursor.getColumnIndex("end_date")
                val createdAtIdx = cursor.getColumnIndex("created_at")

                return model.Region(
                    id = cursor.getInt(idIdx),
                    trip_id = cursor.getInt(tripIdIdx),
                    title = cursor.getString(titleIdx),
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

    fun getScheduleImages(context: Context, scheduleId: Int): List<ImageResult> {
        val dbHelper = SQLiteHelper(context)

        val images = mutableListOf<ImageResult>()
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("""
                SELECT
                    schedule_image.schedule_id AS schedule_id,
                    schedule_image.file_id AS file_id,
                    schedule.title AS title
                FROM schedule_image
                JOIN schedule ON schedule_image.schedule_id = schedule.id
                WHERE schedule_id = ?
            """.trimMargin(), arrayOf(scheduleId.toString()))
            val scheduleIdIdx = cursor.getColumnIndex("schedule_id")
            val fileIdIdx = cursor.getColumnIndex("file_id")
            val titleIdx = cursor.getColumnIndex("title")
            while (cursor.moveToNext()) {
                images.add(
                    ImageResult(
                        schedule_id = cursor.getInt(scheduleIdIdx),
                        file_id = cursor.getString(fileIdIdx),
                        title = cursor.getString(titleIdx
                    )
                ))
            }
            cursor.close()
        }
        return images
    }

    fun getRandomScheduleImages(context: Context, regionId: Int): List<ImageResult> {
        val dbHelper = SQLiteHelper(context)

        val images = mutableListOf<ImageResult>()
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("""
                SELECT *
                FROM (
                    SELECT
                        schedule.id AS schedule_id,
                        schedule_image.file_id AS file_id,
                        schedule.title AS title,
                        ROW_NUMBER() OVER (
                            PARTITION BY schedule.id
                            ORDER BY RANDOM()
                        ) AS rn
                    FROM schedule
                    LEFT JOIN schedule_image ON schedule_image.schedule_id = schedule.id
                    WHERE schedule.region_id = ?
                ) AS sub
                WHERE rn = 1 OR rn IS NULL
                """.trimIndent(),
                arrayOf(regionId.toString())
            )
            val scheduleIdIdx = cursor.getColumnIndex("schedule_id")
            val fileIdIdx = cursor.getColumnIndex("file_id")
            val titleIdx = cursor.getColumnIndex("title")
            while (cursor.moveToNext()) {
                images.add(
                    ImageResult(
                        schedule_id = cursor.getInt(scheduleIdIdx),
                        file_id = cursor.getString(fileIdIdx),
                        title = cursor.getString(titleIdx
                    )
                ))
            }
            cursor.close()
        }
        return images
    }

    fun getRandomRegionImages(context: Context, tripId: Int): List<ImageResult> {
        val dbHelper = SQLiteHelper(context)

        val images = mutableListOf<ImageResult>()
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("""
                SELECT *
                FROM (
                    SELECT
                        schedule_image.schedule_id AS schedule_id,
                        region.id AS region_id,
                        schedule_image.file_id AS file_id,
                        region.title AS title,
                        ROW_NUMBER() OVER (
                            PARTITION BY region.id
                            ORDER BY RANDOM()
                        ) AS rn
                    FROM region
                    LEFT JOIN schedule ON schedule.region_id = region.id
                    LEFT JOIN schedule_image ON schedule_image.schedule_id = schedule.id
                    WHERE region.trip_id = ?
                ) AS sub
                WHERE rn = 1 OR rn IS NULL
                """.trimIndent(),
                arrayOf(tripId.toString())
            )
            val scheduleIdIdx = cursor.getColumnIndex("schedule_id")
            val regionIdIdx = cursor.getColumnIndex("region_id")
            val fileIdIdx = cursor.getColumnIndex("file_id")
            val titleIdx = cursor.getColumnIndex("title")
            while (cursor.moveToNext()) {
                images.add(
                    ImageResult(
                        schedule_id = cursor.getInt(scheduleIdIdx),
                        region_id = cursor.getInt(regionIdIdx),
                        file_id = cursor.getString(fileIdIdx),
                        title = cursor.getString(titleIdx
                    )
                ))
            }
            cursor.close()
        }
        return images
    }

    fun getRandomTripImages(context: Context): List<ImageResult> {
        val dbHelper = SQLiteHelper(context)

        val images = mutableListOf<ImageResult>()
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("""
                SELECT *
                FROM (
                    SELECT
                        trip.id AS trip_id,
                        region.id AS region_id,
                        schedule_image.schedule_id AS schedule_id,
                        schedule_image.file_id AS file_id,
                        region.title AS title,
                        ROW_NUMBER() OVER (
                           PARTITION BY region.id
                           ORDER BY RANDOM()
                        ) AS rn
                    FROM trip
                    LEFT JOIN region ON region.trip_id = trip.id
                    LEFT JOIN schedule ON schedule.region_id = region.id
                    LEFT JOIN schedule_image ON schedule_image.schedule_id = schedule.id
                ) AS sub
                """.trimIndent(),
                null
            )
            val tripIdIdx = cursor.getColumnIndex("trip_id")
            val regionIdIdx = cursor.getColumnIndex("region_id")
            val scheduleIdIdx = cursor.getColumnIndex("schedule_id")
            val fileIdIdx = cursor.getColumnIndex("file_id")
            val titleIdx = cursor.getColumnIndex("title")

            while (cursor.moveToNext()) {
                images.add(
                    ImageResult(
                        trip_id = cursor.getInt(tripIdIdx),
                        region_id = cursor.getInt(regionIdIdx),
                        schedule_id = cursor.getInt(scheduleIdIdx),
                        file_id = cursor.getString(fileIdIdx),
                        title = cursor.getString(titleIdx
                    )
                ))
            }
            cursor.close()
        }
        return images
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

    fun searchFromAll(
        context: Context,
        query: String,
    ): List<MapSearchResult>
    {
        if (query.isBlank()) return emptyList()

        val dbHelper = SQLiteHelper(context)
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("""
                SELECT id, title, "TRIP" AS type
                FROM trip
                WHERE title LIKE ?
                UNION
                SELECT id, title, "REGION" AS type
                FROM region
                WHERE title LIKE ?
                UNION
                SELECT id, title, "SCHEDULE" AS type
                FROM schedule
                WHERE title LIKE ?
                LIMIT 10
                """.trimIndent(),
                arrayOf("%$query%", "%$query%", "%$query%")
            )
            val results = mutableListOf<MapSearchResult>()
            val idIdx = cursor.getColumnIndex("id")
            val titleIdx = cursor.getColumnIndex("title")
            val typeIdx = cursor.getColumnIndex("type")
            while (cursor.moveToNext()) {
                val id = cursor.getInt(idIdx)
                val title = cursor.getString(titleIdx)
                val type = cursor.getString(typeIdx)

                results.add(
                    MapSearchResult(
                        id = id,
                        title = title,
                        type = when (type) {
                            "TRIP" -> MapPinType.TRIP
                            "REGION" -> MapPinType.REGION
                            "SCHEDULE" -> MapPinType.SCHEDULE
                            else -> MapPinType.USER_SELECTED
                        }
                    )
                )
            }

            cursor.close()

            return results
        }
    }

    fun countTrips(context: Context): Int {
        val dbHelper = SQLiteHelper(context)
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT COUNT(*) FROM trip", null)
            if (cursor.moveToFirst()) {
                return cursor.getInt(0)
            }
            cursor.close()
        }
        return 0
    }

    fun countRegions(context: Context): Int {
        val dbHelper = SQLiteHelper(context)
        dbHelper.readableDatabase.use { db ->
            val cursor = db.rawQuery("SELECT COUNT(*) FROM region", null)
            if (cursor.moveToFirst()) {
                return cursor.getInt(0)
            }
            cursor.close()
        }
        return 0
    }

    fun createScheduleImage(context: Context, scheduleId: Int, fileId: String): Long {
        val dbHelper = SQLiteHelper(context)
        dbHelper.writableDatabase.use { db ->
            val values = android.content.ContentValues().apply {
                put("schedule_id", scheduleId)
                put("file_id", fileId)
                put("created_at", System.currentTimeMillis().toString())
            }
            return db.insert("schedule_image", null, values)
        }
    }
}

data class ImageResult(
    val trip_id: Int? = null,
    val region_id: Int? = null,
    val schedule_id: Int? = null,
    val title: String,
    val file_id: String? = null,
)