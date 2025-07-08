package com.example.app.ui.components.popup

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.R
import com.example.app.ui.components.PopupWindow
import com.example.app.ui.components.buttons.ScheduleTypeButton
import com.example.app.ui.components.popup.map_selector.MapSelector
import com.example.app.ui.theme.CustomColors
import com.example.app.util.DatetimeUtil
import com.example.app.util.database.model
import java.util.Calendar

@Composable
fun AddScheduleForm(
    button: @Composable (onClick: () -> Unit) -> Unit = {},
    saveFn: (type: model.ScheduleType, title: String, memo: String, start_datetime: String, end_datetime: String, locValue: PlaceUtil.LocationInfo) -> Unit,
    title: String,
) {
    val context = LocalContext.current

    val calendar = Calendar.getInstance()

    val (selectedType, setSelectedType) = remember { mutableStateOf(model.ScheduleType.SIGHTS) }
    val (nameValue, setNameValue) = remember { mutableStateOf("") }
    val (memoValue, setMemoValue) = remember { mutableStateOf("") }

    val (startDatetimeValue, setStartDatetimeValue) = remember { mutableStateOf("") }
    val (startDateValue, setStartDateValue) = remember { mutableStateOf("") }
    val (startTimeValue, setStartTimeValue) = remember { mutableStateOf("") }
    val (endDatetimeValue, setEndDatetimeValue) = remember { mutableStateOf("") }
    val (endDateValue, setEndDateValue) = remember { mutableStateOf("") }
    val (endTimeValue, setEndTimeValue) = remember { mutableStateOf("") }

    val (locValue, setLocValue) = remember { mutableStateOf<PlaceUtil.LocationInfo?>(null) }
    val (isLocSelecting, setIsLocSelecting) = remember { mutableStateOf(false) }

    // startDateValue, startTimeValue가 변할 때마다 startDatetimeValue를 자동 업데이트 (timestamp 형식)
    LaunchedEffect(startDateValue, startTimeValue) {
        if (startDateValue.isNotBlank() && startTimeValue.isNotBlank()) {
            setStartDatetimeValue(DatetimeUtil.createDatetimeWithDateAndTime(startDateValue, startTimeValue))
        } else {
            setStartDatetimeValue("")
        }
    }

    // endDateValue, endTimeValue가 변할 때마다 endDatetimeValue를 자동 업데이트 (timestamp 형식)
    LaunchedEffect(endDateValue, endTimeValue) {
        if (endDateValue.isNotBlank() && endTimeValue.isNotBlank()) {
            setEndDatetimeValue(DatetimeUtil.createDatetimeWithDateAndTime(endDateValue, endTimeValue))
        } else {
            setEndDatetimeValue("")
        }
    }

    PopupWindow(
        button = button,
        title = title,
        leftBtnLabel ="완료",
        rightBtnLabel = "닫기",
        leftBtnOnClick = { setShowDialog ->
            if (nameValue.isNotBlank() && locValue != null) {
                saveFn(
                    selectedType,
                    nameValue,
                    memoValue,
                    startDatetimeValue,
                    endDatetimeValue,
                    locValue
                )
                setShowDialog(false)
            } else {
                if(nameValue.isBlank()) {
                    Toast.makeText(context, "이름을 입력해 주세요", android.widget.Toast.LENGTH_SHORT).show()
                } else if (locValue == null) {
                    Toast.makeText(context, "위치를 선택해 주세요", android.widget.Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "이름과 위치를 입력해 주세요", android.widget.Toast.LENGTH_SHORT).show()
                }
            }
        },
        rightBtnOnClick = {},
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp)) // 여백

                // ScheduleType 선택
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ScheduleTypeButton(
                        type = model.ScheduleType.SIGHTS,
                        selectedType = selectedType,
                        onClick = { setSelectedType(model.ScheduleType.SIGHTS) }
                    )
                    ScheduleTypeButton(
                        type = model.ScheduleType.HOTEL,
                        selectedType = selectedType,
                        onClick = { setSelectedType(model.ScheduleType.HOTEL) }
                    )
                    ScheduleTypeButton(
                        type = model.ScheduleType.RESTAURANT,
                        selectedType = selectedType,
                        onClick = { setSelectedType(model.ScheduleType.RESTAURANT) }
                    )
                    ScheduleTypeButton(
                        type = model.ScheduleType.ETC,
                        selectedType = selectedType,
                        onClick = { setSelectedType(model.ScheduleType.ETC) }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp)) // 여백

                // 여행 이름
                TextField(
                    value = nameValue,
                    onValueChange = { setNameValue(it) },
                    placeholder = { Text("이름을 입력하세요", color = CustomColors.Gray) },
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
                        focusedTextColor = CustomColors.DarkGray,
                        unfocusedTextColor = CustomColors.DarkGray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp)) // 여백

                // 여행 메모
                TextField(
                    value = memoValue,
                    onValueChange = { setMemoValue(it) },
                    placeholder = { Text("메모를 남기세요", color = CustomColors.Gray) },
                    leadingIcon = {
                        Text(
                            text = "메모",
                            modifier = Modifier.padding(start = 8.dp),
                            color = CustomColors.Black
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = CustomColors.LightGray,
                        unfocusedContainerColor = CustomColors.LighterGray,
                        focusedIndicatorColor =  CustomColors.White,
                        unfocusedIndicatorColor = CustomColors.White,
                        focusedTextColor = CustomColors.DarkGray,
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
                        placeholder = { Text("위치를 선택하세요", color = CustomColors.Gray) },
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
                            contentDescription = "위치 선택",
                            tint = CustomColors.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp)) // 여백

                // 시작 날짜 + 시간 선택
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 날짜 보이는 필드
                    TextField(
                        value = DatetimeUtil.datetimeToString(startDatetimeValue),
                        onValueChange = {},
                        modifier = Modifier.weight(1f), // TextField가 남는 공간 채움
                        placeholder = {
                            when {
                                startDateValue.isBlank() && startTimeValue.isBlank() ->
                                    Text("날짜와 시간을\n선택하세요", color = CustomColors.Gray, lineHeight = 18.sp)
                                startDateValue.isBlank() ->
                                    Text("날짜를 선택하세요", color = CustomColors.Gray)
                                startTimeValue.isBlank() ->
                                    Text("시간을 선택하세요", color = CustomColors.Gray)
                            }
                        },
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

                    // 날짜 선택 버튼
                    IconButton(
                        onClick = {
                            val datePicker = DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    val pickedCal = Calendar.getInstance().apply {
                                        set(year, month, dayOfMonth)
                                    }
                                    val format = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                                    setStartDateValue(format.format(pickedCal.time))
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )
                            datePicker.show()
                        },
                        modifier = Modifier
                            .height(45.dp)
                            .width(45.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "추가",
                            tint = CustomColors.Black
                        )
                    }

                    // 시간 선택 버튼
                    IconButton(
                        onClick = {
                            val timePicker = TimePickerDialog(
                                context,
                                { _, hourOfDay, minute ->
                                    val pickedCal = Calendar.getInstance().apply {
                                        set(Calendar.HOUR_OF_DAY, hourOfDay)
                                        set(Calendar.MINUTE, minute)
                                    }
                                    val format = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                                    setStartTimeValue(format.format(pickedCal.time))
                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                false  // 24시간 형식
                            )
                            timePicker.show()
                        },
                        modifier = Modifier
                            .height(45.dp)
                            .width(45.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.all_clock),
                            contentDescription = "시간 선택",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp)) // 여백

                // 종료 날짜 + 시간 선택
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 날짜 보이는 필드
                    TextField(
                        value = DatetimeUtil.datetimeToString(endDatetimeValue),
                        onValueChange = {},
                        modifier = Modifier.weight(1f), // TextField가 남는 공간 채움
                        placeholder = {
                            when {
                                endDateValue.isBlank() && endTimeValue.isBlank() ->
                                    Text("날짜와 시간을\n선택하세요", color = CustomColors.Gray, lineHeight = 18.sp)
                                endDateValue.isBlank() ->
                                    Text("날짜를 선택하세요", color = CustomColors.Gray)
                                endTimeValue.isBlank() ->
                                    Text("시간을 선택하세요", color = CustomColors.Gray)
                            }
                        },
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

                    // 날짜 선택 버튼
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
                            contentDescription = "추가",
                            tint = CustomColors.Black
                        )
                    }

                    // 시간 선택 버튼
                    IconButton(
                        onClick = {
                            val timePicker = TimePickerDialog(
                                context,
                                { _, hourOfDay, minute ->
                                    val pickedCal = Calendar.getInstance().apply {
                                        set(Calendar.HOUR_OF_DAY, hourOfDay)
                                        set(Calendar.MINUTE, minute)
                                    }
                                    val format = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                                    setEndTimeValue(format.format(pickedCal.time))
                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                false  // 24시간 형식
                            )
                            timePicker.show()
                        },
                        modifier = Modifier
                            .height(45.dp)
                            .width(45.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.all_clock),
                            contentDescription = "시간 선택",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    )
    if (isLocSelecting) {
        MapSelector(
            onDismiss = {
                setLocValue(null)
                setIsLocSelecting(false) // 위치 선택 취소
            },
            setIsLocSelecting = setIsLocSelecting,
            locValue = locValue,
            setLocValue = setLocValue,
        )
    }
}