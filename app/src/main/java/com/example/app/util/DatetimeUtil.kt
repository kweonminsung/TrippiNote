package com.example.app.util

object DatetimeUtil {
    fun timestampToMonthDay(timestamp: String): String {
        return if (timestamp.isBlank()) {
            ""
        } else {
            val parts = timestamp.split("T")
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
}