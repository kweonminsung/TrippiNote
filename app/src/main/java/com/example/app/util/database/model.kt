package com.example.app.util.database

import androidx.compose.ui.graphics.Color
import com.example.app.ui.theme.CustomColors
import com.example.app.util.database.model.TransportType

class model {
    data class Wishlist(
        val done: Boolean,
        val content: String,
    )

    data class ChecklistItem(
        val done: Boolean,
        val content: String,
    )

    data class Transport(
        val id: Int,
        val region_id: Int,
        val from_schedule_id: Int,
        val to_schedule_id: Int,
        val type: TransportType,
        val duration: String? = null,
        val created_at: String,
        val memo: String = "",
    )
    data class Schedule(
        val id: Int,
        val region_id: Int,
        val place_id: Int? = null,
        val title: String,
        val memo: String = "",
        val lat: Double,
        val lng: Double,
        val start_date: String? = null,
        val end_date: String? = null,
        val created_at: String,
    )

    data class Region(
        val id: Int,
        val trip_id: Int,
        val title: String,
        val lat: Double,
        val lng: Double,
        val start_date: String? = null,
        val end_date: String? = null,
        val created_at: String,
    )

    data class Trip(
        val id: Int,
        val title: String,
        val lat: Double,
        val lng: Double,
        val start_date: String? = null,
        val end_date: String? = null,
        val created_at: String,
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

}