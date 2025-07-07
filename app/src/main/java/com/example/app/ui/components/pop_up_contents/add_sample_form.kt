package com.example.app.ui.components.pop_up_contents

import PlaceUtil
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.app.ui.components.PopupWindow
import com.example.app.ui.components.pop_up_contents.map_selector.MapSelector
import com.example.app.ui.theme.CustomColors
import com.example.app.util.database.MapRepository
import com.example.app.util.database.model
import java.time.Instant
import java.util.Calendar

@Composable
fun AddSampleForm(
    button: @Composable (onClick: () -> Unit) -> Unit = {},
    title: String,
) {
    val context = LocalContext.current

    val calendar = Calendar.getInstance()

    val (nameValue, setNameValue) = remember { mutableStateOf("") }
    val (startDateValue, setStartDateValue) = remember { mutableStateOf("") }
    val (endDateValue, setEndDateValue) = remember { mutableStateOf("") }
    val (locValue, setLocValue) = remember { mutableStateOf<PlaceUtil.LocationInfo?>(null) }
    val (isLocSelecting, setIsLocSelecting) = remember { mutableStateOf(false) }

    PopupWindow(
        button,
        title,
        "완료", // 편집 or 완료
        "닫기", // 닫기 or 삭제
        CustomColors.Blue,
        onSubmit = { setShowDialog ->
            if (nameValue.isNotBlank() && locValue != null) {
                MapRepository.createTrip(
                    context,
                    model.Trip(
                        id = -1,
                        title = nameValue,
                        start_date = startDateValue,
                        end_date = endDateValue,
                        lat = locValue.position.latitude,
                        lng = locValue.position.longitude,
                        created_at = Instant.now().toString(),
                    )
                )
                setShowDialog(false)
            } else {
                android.widget.Toast.makeText(context, "모든 값을 입력해 주세요", android.widget.Toast.LENGTH_SHORT).show()
            }
        },
        onCancel = {},
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp)) // 여백

                //여행 이름
                TextField(
                    value = nameValue,
                    onValueChange = { setNameValue(it) },
                    placeholder = { Text("이름을 입력하세요...", color = CustomColors.Gray) },
                    leadingIcon = {
                        Text(
                            text = "이름",
                            modifier = Modifier.padding(start = 8.dp),
                            color = CustomColors.Black
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = CustomColors.LightGray,
                        unfocusedContainerColor = CustomColors.LighterGray,
                        focusedIndicatorColor =  CustomColors.White,
                        unfocusedIndicatorColor = CustomColors.White,
                        focusedTextColor = CustomColors.DarkGray,      // 입력값 색상 (포커스 O)
                        unfocusedTextColor = CustomColors.DarkGray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp)) // 여백

                // 지도에서 선택
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 위치 보이는 필드
                    TextField(
                        value = locValue?.title ?: "",
                        onValueChange = {},
                        modifier = Modifier.weight(1f), // TextField가 남는 공간 채움
                        placeholder = { Text("위치를 선택하세요...", color = CustomColors.Gray) },
                        leadingIcon = {
                            Text(
                                text = "위치",
                                modifier = Modifier.padding(start = 8.dp),
                                color = CustomColors.Black
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            disabledContainerColor = CustomColors.LighterGray,
                            disabledTextColor = CustomColors.DarkGray,
                        ),
                        enabled = false,
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // 여백
                    // 지도에서 위치 선택 버튼
                    IconButton(
                        onClick = {
                            setIsLocSelecting(true)
                        },
                        modifier = Modifier
                            .height(45.dp)
                            .width(45.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = "위치 선택"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp)) // 여백

                // start 날짜 선택
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 날짜 보이는 필드
                    TextField(
                        value = startDateValue,
                        onValueChange = {},
                        modifier = Modifier.weight(1f), // TextField가 남는 공간 채움
                        placeholder = { Text("날짜를 선택하세요...", color = CustomColors.Gray) },
                        leadingIcon = {
                            Text(
                                text = "시작",
                                modifier = Modifier.padding(start = 8.dp),
                                color = CustomColors.Black
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            disabledContainerColor = CustomColors.LighterGray,
                            disabledTextColor = CustomColors.DarkGray,
                        ),
                        enabled = false,
                    )

                    Spacer(modifier = Modifier.width(8.dp)) // 여백

                    // 캘린더에서 날짜 선택 버튼
                    IconButton(
                        onClick = {
                            val datePicker = DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    val pickedCal = java.util.Calendar.getInstance().apply {
                                        set(year, month, dayOfMonth)
                                    }
                                    val format = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                                    setStartDateValue(format.format(pickedCal.time))
                                },
                                calendar.get(java.util.Calendar.YEAR),
                                calendar.get(java.util.Calendar.MONTH),
                                calendar.get(java.util.Calendar.DAY_OF_MONTH)
                            )
                            datePicker.show()
                        },
                        modifier = Modifier
                            .height(45.dp)
                            .width(45.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "추가"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp)) // 여백

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 날짜 보이는 필드
                    TextField(
                        value = endDateValue,
                        onValueChange = {},
                        modifier = Modifier.weight(1f), // TextField가 남는 공간 채움
                        placeholder = { Text("날짜를 선택하세요...", color = CustomColors.Gray) },
                        leadingIcon = {
                            Text(
                                text = "종료",
                                modifier = Modifier.padding(start = 8.dp),
                                color = CustomColors.Black
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            disabledContainerColor = CustomColors.LighterGray,
                            disabledTextColor = CustomColors.DarkGray,
                        ),
                        enabled = false,
                    )

                    Spacer(modifier = Modifier.width(8.dp)) // 여백

                    // 캘린더에서 날짜 선택 버튼
                    IconButton(
                        onClick = {
                            val datePicker = DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    val pickedCal = java.util.Calendar.getInstance().apply {
                                        set(year, month, dayOfMonth)
                                    }
                                    val format = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                                    setEndDateValue(format.format(pickedCal.time))
                                },
                                calendar.get(java.util.Calendar.YEAR),
                                calendar.get(java.util.Calendar.MONTH),
                                calendar.get(java.util.Calendar.DAY_OF_MONTH)
                            )
                            datePicker.show()
                        },
                        modifier = Modifier
                            .height(45.dp)
                            .width(45.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "추가"
                        )
                    }
                }
            }
        }
    )
    if (isLocSelecting) {
        MapSelector(
            setIsLocSelecting = setIsLocSelecting,
            setLocValue = setLocValue,
        )
    }
}