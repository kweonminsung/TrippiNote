package com.example.app.ui.components.map


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.app.ui.theme.CustomColors

@Composable
fun TransportTimeForm(
    duration: String? = null,
    setIsTransportDurationSetting: (Boolean) -> Unit,
    saveTimeFn: (hour: String, minute: String) -> Unit,
) {
    val savedHour = duration?.split(":")?.getOrNull(0) ?: "0"
    val savedMinute = duration?.split(":")?.getOrNull(1) ?: "0"

    val (hourValue, setHourValue) = remember { mutableStateOf(savedHour) } // 시간
    val (minuteValue, setMinuteValue) = remember { mutableStateOf(savedMinute) } // 분

    Dialog(onDismissRequest = { setIsTransportDurationSetting(false) }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = CustomColors.White,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "시간 입력",
                    fontSize = 20.sp,
                    color = CustomColors.Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = hourValue,
                        onValueChange = { value ->
                            if (value.length <= 2) {
                                val hour = value.toIntOrNull()
                                if (hour == null || hour >= 0) {
                                    setHourValue(value)
                                }
                            }
                        },
                        suffix = { Text("시") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.width(100.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = CustomColors.Black,
                            unfocusedTextColor = CustomColors.Black,
                        )
                    )

                    OutlinedTextField(
                        value = minuteValue,
                        onValueChange = { value ->
                            if (value.length <= 2) {
                                val minute = value.toIntOrNull()
                                if (minute == null || minute in 0..59) {
                                    setMinuteValue(value)
                                }
                            }
                        },
                        suffix = { Text("분") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.width(100.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = CustomColors.Black,
                            unfocusedTextColor = CustomColors.Black,
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                ) {
                    Button(
                        onClick = {
                            if (hourValue.isBlank() && minuteValue.isBlank()) {
                                return@Button
                            }
                            if (hourValue.toIntOrNull() == null && minuteValue.toIntOrNull() == null) {
                                return@Button
                            }

                            saveTimeFn(hourValue, minuteValue)
                            setIsTransportDurationSetting(false)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CustomColors.LightGray,
                            contentColor = CustomColors.Black
                        )
                    ) {
                        Text(text = "완료")
                    }
                    Button(
                        onClick = { setIsTransportDurationSetting(false) },
                        modifier = Modifier
                            .padding(top = 0.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CustomColors.Red,
                            contentColor = CustomColors.White
                        )
                    ) {
                        Text(text = "취소")
                    }
                }
            }
        }
    }
}