package com.example.app.ui.pages.map

import android.util.Log
import com.example.app.ui.components.map.MapPin
import com.example.app.ui.components.map.MapPinType
import com.example.app.ui.components.map.TransportPin
import com.example.app.util.DatetimeUtil
import com.example.app.util.database.MapRepository
import com.example.app.util.database.model
import com.google.android.gms.maps.model.LatLng

object MapTabData {
    fun getSessionTransportPinsByZoomRate(
        context: android.content.Context,
        zoomLevel: Float
    ): List<TransportPin> {
//        for ( i in MapRepository.getAllTransports(context)) {
//            Log.d("MapTabData", "Transport: ${i.from_schedule_id} -> ${i.to_schedule_id}, type: ${i.type}")
//        }
        return when (zoomLevel) {
            ZOOM_LEVEL.CITY -> {
                MapRepository.getAllTransports(context).map { transport ->
                    val fromSchedule = MapRepository.getScheduleById(
                        context,
                        transport.from_schedule_id
                    ) as model.Schedule
                    val toSchedule = MapRepository.getScheduleById(
                        context,
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
        val pins = when (zoomLevel) {
            ZOOM_LEVEL.CITY -> {
                val schedules = MapRepository.getAllSchedules(context)
                schedules.map { schedule ->
                    MapPin(
                        id = schedule.id,
                        type = MapPinType.SCHEDULE,
                        position = LatLng(schedule.lat, schedule.lng),
                        title = schedule.title,
                        subtitle = schedule.start_datetime?.let {
                            DatetimeUtil.datetimeToMonthDay(it)
                        }
                    )
                }
            }

            ZOOM_LEVEL.COUNTRY -> {
                val regions = MapRepository.getAllRegions(context)
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
                val trips = MapRepository.getTrips(context)
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
        tripId: Int
    ): List<model.Region> {
        return MapRepository.getRegions(context, tripId)
    }

    fun getSessionSchedules(
        context: android.content.Context,
        regionId: Int
    ): List<model.Schedule> {
        return MapRepository.getSchedules(context, regionId)
    }

    fun getSessionTransports(
        context: android.content.Context,
        regionId: Int
    ): List<model.Transport> {
        return MapRepository.getTransports(context, regionId)
    }

    fun getSessionTransportByFromTo(
        context: android.content.Context,
        regionId: Int,
        fromScheduleId: Int,
        toScheduleId: Int
    ): model.Transport? {
        return MapRepository.getTransportByFromTo(
            context,
            regionId,
            fromScheduleId,
            toScheduleId
        )
    }
}