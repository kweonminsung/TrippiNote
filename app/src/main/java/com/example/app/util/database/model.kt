package com.example.app.util.database

class model {
    data class Wishlist(
        val id: Int,
        val done: Boolean,
        val content: String,
    )

    data class Checklist(
        val id: Int,
        val done: Boolean,
        val content: String,
    )

    data class Transport(
        val id: Int,
        val region_id: Int,
        val from_schedule_id: Int,
        val to_schedule_id: Int,
        val type: TransportType,
        val duration: String? = null, // 00:30:00
        val created_at: String, // 2024-04-01T12:00:00Z
        val memo: String = "",
    )
    data class Schedule(
        val id: Int,
        val type: ScheduleType,
        val region_id: Int,
        val place_id: Int? = null,
        val title: String,
        val memo: String = "",
        val lat: Double,
        val lng: Double,
        val start_datetime: String? = null, // 2024-04-01T12:00:00Z
        val end_datetime: String? = null, // 2024-04-01T12:00:00Z
        val created_at: String, // 2024-04-01T12:00:00Z
    )

    data class ScheduleImage(
        val id: Int,
        val schedule_id: Int,
        val file_id: String,
        val created_at: String, // 2024-04-01T12:00:00Z
    )

    data class File(
        val id: String, // UUID
        val name: String, // original file name
        val size: Long, // file size in bytes
        val mime_type: String, // e.g., "image/jpeg"
        val created_at: String, // 2024-04-01T12:00:00Z
    )

    data class Region(
        val id: Int,
        val trip_id: Int,
        val title: String,
        val lat: Double,
        val lng: Double,
        val start_date: String? = null, // 2024-04-01
        val end_date: String? = null, // 2024-04-01
        val created_at: String,
    )

    data class Trip(
        val id: Int,
        val title: String,
        val lat: Double,
        val lng: Double,
        val start_date: String? = null, // 2024-04-01
        val end_date: String? = null, // 2024-04-01
        val created_at: String, // 2024-04-01T12:00:00Z
    )

    enum class TransportType {
        WALKING,
        BICYCLE,
        CAR,
        BUS,
        TRAIN,
        SUBWAY,
        AIRPLANE;
    }

    enum class ScheduleType {
        SIGHTS,
        HOTEL,
        RESTAURANT,
        ETC,
    }

}