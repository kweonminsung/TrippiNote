package com.example.app.util.database

import androidx.compose.ui.graphics.Color
import com.example.app.ui.theme.CustomColors
import com.example.app.R

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

        fun toColor(): Color {
            return when (this) {
                WALKING -> CustomColors.Blue
                BICYCLE -> CustomColors.Green
                CAR -> CustomColors.Red
                BUS -> CustomColors.Yellow
                TRAIN -> CustomColors.Orange
                SUBWAY -> CustomColors.LightGray
                AIRPLANE -> CustomColors.Gray
            }
        }

        fun toStringKor(): String {
            return when (this) {
                WALKING -> "도보"
                BICYCLE -> "자전거"
                CAR -> "자동차"
                BUS -> "버스"
                TRAIN -> "기차"
                SUBWAY -> "지하철"
                AIRPLANE -> "비행기"
            }
        }
    }

    enum class ScheduleType {
        SIGHTS,
        HOTEL,
        RESTAURANT,
        ETC;

        fun toColor(): Color {
            return when (this) {
                SIGHTS -> CustomColors.Blue
                HOTEL -> CustomColors.DarkGray
                RESTAURANT -> CustomColors.Orange
                ETC -> CustomColors.Red
            }
        }

        fun toStringKor(): String {
            return when (this) {
                SIGHTS -> "관광"
                HOTEL -> "숙소"
                RESTAURANT -> "음식점"
                ETC -> "기타"
            }
        }

        fun toIcon(): Int {
            return when (this) {
                SIGHTS -> R.drawable.map_sights
                HOTEL -> R.drawable.map_hotel
                RESTAURANT -> R.drawable.map_restaurant
                ETC -> R.drawable.map_etc
            }
        }
    }

}