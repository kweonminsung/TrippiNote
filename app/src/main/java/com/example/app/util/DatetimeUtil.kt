package com.example.app.util

object DatetimeUtil {
    fun datetimeToMonthDay(datetime: String): String {
        return if (datetime.isBlank()) {
            ""
        } else {
            val parts = datetime.split("T")
            if (parts.size < 2) return ""
            val datePart = parts[0]
            val dateParts = datePart.split("-")
            if (dateParts.size < 2) return ""

            val month = dateParts[1].toIntOrNull()?.toString() ?: dateParts[1]
            val day = dateParts[2].toIntOrNull()?.toString() ?: dateParts[2]

            "${month}월 ${day}일"
        }
    }

    fun dateToYearMonth(date: String): String {
        return if (date.isBlank()) {
            ""
        } else {
            val parts = date.split("-")
            if (parts.size < 2) return ""

            val year = parts[0]
            val shortYear = if (year.length == 4) year.substring(2, 4) else year
            val month = parts[1].toIntOrNull()?.toString() ?: parts[1]

            "${shortYear}년 ${month}월"
        }
    }

    fun dateToYearSeason(date: String): String {
        return if (date.isBlank()) {
            ""
        } else {
            val parts = date.split("-")
            if (parts.size < 2) return ""

            val month = parts[1].toIntOrNull() ?: return ""
            val season = when (month) {
                3, 4, 5 -> "봄"
                6, 7, 8 -> "여름"
                9, 10, 11 -> "가을"
                else -> "겨울"
            }
            val year = parts[0]
            val shortYear = if (year.length == 4) year.substring(2, 4) else year

            "${shortYear}년 $season"
        }
    }

    fun dateToDotDate(date: String): String {
        return if (date.isBlank()) {
            ""
        } else {
            val parts = date.split("-")
            if (parts.size < 3) return ""

            val year = parts[0]
            val shortYear = if (year.length == 4) year.substring(2, 4) else year
            val month = parts[1].toIntOrNull()?.toString() ?: parts[1]
            val day = parts[2].toIntOrNull()?.toString() ?: parts[2]

            "$shortYear.$month.$day"
        }
    }

    fun datetimeToTime(datetime: String): String {
        return if (datetime.isBlank()) {
            ""
        } else {
            val parts = datetime.split("T")
            if (parts.size < 2) return ""
            val timePart = parts[1]
            val timeParts = timePart.split(":")
            if (timeParts.size < 2) return ""

            val hour = timeParts[0].toIntOrNull()?.toString() ?: timeParts[0]
            val minute = timeParts[1].padStart(2, '0')

            "$hour:$minute"
        }
    }

    fun timeToHourMinute(time: String): String {
        if (time.isBlank()) return ""
        val parts = time.split(":")
        if (parts.size < 2) return ""
        val hour = parts[0].toIntOrNull() ?: return ""
        val minute = parts[1].toIntOrNull() ?: 0
        return when {
            hour > 0 && minute > 0 -> "${hour}시간 ${minute}분"
            hour > 0 -> "${hour}시간"
            minute > 0 -> "${minute}분"
            else -> "0분"
        }
    }

    fun daysBetween(startDate: String, endDate: String): Int {
        if (startDate.isBlank() || endDate.isBlank()) return 0
        val startParts = startDate.split("-")
        val endParts = endDate.split("-")
        if (startParts.size < 3 || endParts.size < 3) return 0

        val startYear = startParts[0].toIntOrNull() ?: return 0
        val startMonth = startParts[1].toIntOrNull() ?: return 0
        val startDay = startParts[2].toIntOrNull() ?: return 0

        val endYear = endParts[0].toIntOrNull() ?: return 0
        val endMonth = endParts[1].toIntOrNull() ?: return 0
        val endDay = endParts[2].toIntOrNull() ?: return 0

        val startDateObj = java.time.LocalDate.of(startYear, startMonth, startDay)
        val endDateObj = java.time.LocalDate.of(endYear, endMonth, endDay)

        return java.time.temporal.ChronoUnit.DAYS.between(startDateObj, endDateObj).toInt()
    }
}