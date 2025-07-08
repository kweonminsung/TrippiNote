package com.example.app.ui.components.popup

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.app.util.database.model
import com.example.app.ui.components.buttons.TransportTypeButton
import com.example.app.ui.theme.CustomColors

@Composable
fun TransportAddForm(
    onDismiss: () -> Unit = {},
    type: model.TransportType? = null,
    saveFn: (type: model.TransportType, from_schedule_id: Int, to_schedule_id: Int) -> Unit,
) {
    val (transportTypeValue, setTransportTypeValue) = remember { mutableStateOf(type) }


    BackHandler(onBack = onDismiss) // 뒤로가기 버튼 핸들러
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Box(
            modifier = androidx.compose.ui.Modifier
                .padding(16.dp)
                .background(
                    color = CustomColors.White,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column() {
                TransportTypeButton(
                    type = model.TransportType.WALKING,
                    selected = transportTypeValue == model.TransportType.WALKING,
                    onClick = { setTransportTypeValue(model.TransportType.WALKING) }
                )
                TransportTypeButton(
                    type = model.TransportType.BICYCLE,
                    selected = transportTypeValue == model.TransportType.BICYCLE,
                    onClick = { setTransportTypeValue(model.TransportType.BICYCLE) }
                )
                TransportTypeButton(
                    type = model.TransportType.CAR,
                    selected = transportTypeValue == model.TransportType.CAR,
                    onClick = { setTransportTypeValue(model.TransportType.CAR) }
                )
                TransportTypeButton(
                    type = model.TransportType.BUS,
                    selected = transportTypeValue == model.TransportType.BUS,
                    onClick = { setTransportTypeValue(model.TransportType.BUS) }
                )
                TransportTypeButton(
                    type = model.TransportType.TRAIN,
                    selected = transportTypeValue == model.TransportType.TRAIN,
                    onClick = { setTransportTypeValue(model.TransportType.TRAIN) }
                )
                TransportTypeButton(
                    type = model.TransportType.SUBWAY,
                    selected = transportTypeValue == model.TransportType.SUBWAY,
                    onClick = { setTransportTypeValue(model.TransportType.SUBWAY) }
                )
                TransportTypeButton(
                    type = model.TransportType.AIRPLANE,
                    selected = transportTypeValue == model.TransportType.AIRPLANE,
                    onClick = { setTransportTypeValue(model.TransportType.AIRPLANE) }
                )
                Button(
                    onClick = { onDismiss() },
                    modifier = Modifier
                        .padding(top = 0.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.Red,
                        contentColor = CustomColors.White
                    )
                ) {
                    Text(
                        text = "취소",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}