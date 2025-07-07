package com.example.app.ui.pages.map

import com.example.app.ui.components.map.MapPin
import com.example.app.ui.components.map.MapPinType
import com.example.app.ui.components.map.TransportPin
import com.example.app.util.DatetimeUtil
import com.example.app.util.database.MapRepository
import com.example.app.util.database.SQLiteHelper
import com.example.app.util.database.model
import com.google.android.gms.maps.model.LatLng

object MapTabData {
    fun getSessionTransportPinsByZoomRate(
        context: android.content.Context,
        zoomLevel: Float
    ): List<TransportPin> {
        val dbHelper = SQLiteHelper(context)

        return when (zoomLevel) {
            ZOOM_LEVEL.CITY -> {
                MapRepository.getAllTransports(dbHelper).map { transport ->
                    val fromSchedule = MapRepository.getScheduleById(
                        dbHelper,
                        transport.from_schedule_id
                    ) as model.Schedule
                    val toSchedule = MapRepository.getScheduleById(
                        dbHelper,
                        transport.to_schedule_id
                    ) as model.Schedule

                    TransportPin(
                        from = LatLng(fromSchedule.lat, fromSchedule.lng),
                        to = LatLng(toSchedule.lat, toSchedule.lng),
                        text = "도보로 이동 ${fromSchedule.id} -> ${toSchedule.id}",
                        transportType = transport.type,
                    )
                }
            }

            else -> emptyList()
        }
    }

    // 저장된 핀 목록을 가져오기(여행, 지역, 일정)
    fun getSessionPinsByZoomRate(
        context: android.content.Context,
        zoomLevel: Float
    ): List<MapPin> {
        val dbHelper = SQLiteHelper(context)

        val pins = when (zoomLevel) {
            ZOOM_LEVEL.CITY -> {
                val schedules = MapRepository.getAllSchedules(dbHelper)
                schedules.map { schedule ->
                    MapPin(
                        id = schedule.id,
                        type = MapPinType.SCHEDULE,
                        position = LatLng(schedule.lat, schedule.lng),
                        title = schedule.title,
                        subtitle = schedule.start_date?.let {
                            DatetimeUtil.timestampToMonthDay(it)
                        }
                    )
                }
            }

            ZOOM_LEVEL.COUNTRY -> {
                val regions = MapRepository.getAllRegions(dbHelper)
                regions.map { region ->
                    MapPin(
                        id = region.id,
                        type = MapPinType.REGION,
                        position = LatLng(region.lat, region.lng),
                        title = region.title,
                        subtitle = region.start_date?.let {
                            DatetimeUtil.dateToYearSeason(it)
                        }
                    )
                }
            }

            else -> {
                val trips = MapRepository.getTrips(dbHelper)
                trips.map { trip ->
                    MapPin(
                        id = trip.id,
                        type = MapPinType.TRIP,
                        position = LatLng(trip.lat, trip.lng),
                        title = trip.title,
                        subtitle = trip.start_date?.let {
                            DatetimeUtil.dateToYearSeason(it)
                        }
                    )
                }
            }
        }

        return pins
    }

    fun getSessionRegions(
        context: android.content.Context,
        regionId: Int
    ): List<model.Region> {
        val dbHelper = SQLiteHelper(context)
        return MapRepository.getRegions(dbHelper, regionId)
    }

    fun getSessionSchedules(
        context: android.content.Context,
        regionId: Int
    ): List<model.Schedule> {
        val dbHelper = SQLiteHelper(context)
        return MapRepository.getSchedules(dbHelper, regionId)
    }
}