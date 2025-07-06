package com.example.app.type

import androidx.compose.ui.graphics.Color
import com.example.app.ui.theme.CustomColors

enum class TransportType {
    WALKING,
    BICYCLE,
    CAR,
    BUS,
    TRAIN,
    SUBWAY,
    AIRPLANE;
}

fun TransportType.toColor(): Color {
    return when (this) {
        TransportType.WALKING -> CustomColors.Blue
        TransportType.BICYCLE -> CustomColors.Green
        TransportType.CAR -> CustomColors.Red
        TransportType.BUS -> CustomColors.Yellow
        TransportType.TRAIN -> CustomColors.Orange
        TransportType.SUBWAY -> CustomColors.LightGray
        TransportType.AIRPLANE -> CustomColors.Gray
    }
}

fun TransportType.toStringKor(): String {
    return when (this) {
        TransportType.WALKING -> "도보"
        TransportType.BICYCLE -> "자전거"
        TransportType.CAR -> "자동차"
        TransportType.BUS -> "버스"
        TransportType.TRAIN -> "기차"
        TransportType.SUBWAY -> "지하철"
        TransportType.AIRPLANE -> "비행기"
    }
}