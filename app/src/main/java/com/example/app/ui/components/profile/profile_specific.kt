package com.example.app.ui.components.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.example.app.LocalSession
import com.example.app.R
import com.example.app.ui.theme.CustomColors
import com.example.app.util.KeyValueStore

@Composable
fun ProfileSpecific(onDismiss: () -> Unit = {}) {
    val context = LocalContext.current
    val sessionData = LocalSession.current.value

    val (isNameSetting, setIsNameSetting) = remember { mutableStateOf(false) }
    val (isSetting, setIsSetting) = remember { mutableStateOf(false) }

    val (name, setName) = remember { mutableStateOf(sessionData.user.name) }
    val (email, setEmail) = remember { mutableStateOf(sessionData.user.email) }
    val (phone, setPhone) = remember { mutableStateOf(sessionData.user.phone) }
    val (birthdate, setBirthdate) = remember { mutableStateOf(sessionData.user.birthdate) }
    val (englishName, setEnglishName) = remember { mutableStateOf(sessionData.user.english_name) }
    val (passportNumber, setPassportNumber) = remember { mutableStateOf(sessionData.user.passport_number) }
    val (passportExpiry, setPassportExpiry) = remember { mutableStateOf(sessionData.user.passport_expiry) }

    BackHandler(onBack = onDismiss) // 뒤로가기 버튼 핸들러
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CustomColors.White),
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = 20.dp,
                        vertical = 8.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            bottom = 32.dp,
                        )
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "프로필 상세",
                        fontSize = 20.sp,
                        color = CustomColors.Black,
                    )
                    Image(
                        painter = painterResource(id = R.drawable.all_exit),
                        contentDescription = "",
                        modifier = Modifier
                            .size(16.dp)
                            .clickable {
                                onDismiss()
                            },
                    )

                }

                Image(
                    painter = painterResource(id = R.drawable.sample_trip),
                    contentDescription = "",
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .shadow(
                            elevation = 1.dp,
                            shape = RoundedCornerShape(60.dp),
                        )
                        .background(CustomColors.White),
                    contentScale = ContentScale.Crop
                )

                // 이름 표시
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = name,
                        fontSize = 24.sp,
                        color = CustomColors.Black
                    )
                    Image(
                        painter = painterResource(id = R.drawable.profile_edit),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(16.dp)
                            .clickable {
                                setIsNameSetting(true)
                            },
                    )
                }


                Button(
                    onClick = {
                        if(isSetting) {
                            // 프로필 저장 로직
                            sessionData.user.name = name
                            sessionData.user.email = email
                            sessionData.user.phone = phone
                            sessionData.user.birthdate = birthdate
                            sessionData.user.english_name = englishName
                            sessionData.user.passport_number = passportNumber
                            sessionData.user.passport_expiry = passportExpiry

                            KeyValueStore.saveBulk(context, sessionData)
                        }

                        setIsSetting(!isSetting)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.LightGray,
                        contentColor = CustomColors.Black
                    )
                ) {
                    Text(
                        text = if(isSetting) "프로필 저장" else "프로필 수정",
                        fontSize = 16.sp
                    )
                }
                if(isSetting) {
                    Button(
                        onClick = { setIsSetting(false) },
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

                Column(
                    ) {
                    if(isSetting || email.isNotEmpty()) {
                        TextField(
                            value = email,
                            onValueChange = setEmail,
                            placeholder = {
                                Text(
                                    text = "이메일을 입력하세요",
                                    color = CustomColors.Gray
                                )
                            },
                            leadingIcon = {
                                Text(
                                    text = "메일",
                                    modifier = Modifier.padding(end = 16.dp),
                                    color = CustomColors.Black,
                                    fontSize = 16.sp
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = CustomColors.White,
                                focusedTextColor = CustomColors.DarkGray,
                                unfocusedContainerColor = CustomColors.White,
                                unfocusedTextColor = CustomColors.DarkGray,
                                disabledContainerColor = CustomColors.White,
                                disabledTextColor = CustomColors.DarkGray,
                            ),
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                            ),
                            enabled = isSetting
                        )
                    }
                    if(isSetting || phone.isNotEmpty()) {
                        TextField(
                            value = phone,
                            onValueChange = setPhone,
                            leadingIcon = {
                                Text(
                                    text = "전화번호",
                                    modifier = Modifier.padding(end = 16.dp),
                                    color = CustomColors.Black,
                                    fontSize = 16.sp
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = CustomColors.White,
                                focusedTextColor = CustomColors.DarkGray,
                                unfocusedContainerColor = CustomColors.White,
                                unfocusedTextColor = CustomColors.DarkGray,
                                disabledContainerColor = CustomColors.White,
                                disabledTextColor = CustomColors.DarkGray,
                            ),
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                            ),
                            enabled = isSetting,
                        )
                    }
                    if(isSetting || birthdate.isNotEmpty()) {
                        TextField(
                            value = birthdate,
                            onValueChange = setBirthdate,
                            leadingIcon = {
                                Text(
                                    text = "생년월일",
                                    modifier = Modifier.padding(end = 16.dp),
                                    color = CustomColors.Black,
                                    fontSize = 16.sp
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = CustomColors.White,
                                focusedTextColor = CustomColors.DarkGray,
                                unfocusedContainerColor = CustomColors.White,
                                unfocusedTextColor = CustomColors.DarkGray,
                                disabledContainerColor = CustomColors.White,
                                disabledTextColor = CustomColors.DarkGray,
                            ),
                            enabled = isSetting
                        )
                    }
                    if(isSetting || englishName.isNotEmpty()) {
                        TextField(
                            value = englishName,
                            onValueChange = setEnglishName,
                            leadingIcon = {
                                Text(
                                    text = "영문 이름",
                                    modifier = Modifier.padding(end = 16.dp),
                                    color = CustomColors.Black,
                                    fontSize = 16.sp
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = CustomColors.White,
                                focusedTextColor = CustomColors.DarkGray,
                                unfocusedContainerColor = CustomColors.White,
                                unfocusedTextColor = CustomColors.DarkGray,
                                disabledContainerColor = CustomColors.White,
                                disabledTextColor = CustomColors.DarkGray,
                            ),
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                            ),
                            enabled = isSetting
                        )
                    }
                    if(isSetting || passportNumber.isNotEmpty()) {
                        TextField(
                            value = passportNumber,
                            onValueChange = setPassportNumber,
                            leadingIcon = {
                                Text(
                                    text = "여권 번호",
                                    modifier = Modifier.padding(end = 16.dp),
                                    color = CustomColors.Black,
                                    fontSize = 16.sp
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = CustomColors.White,
                                focusedTextColor = CustomColors.DarkGray,
                                unfocusedContainerColor = CustomColors.White,
                                unfocusedTextColor = CustomColors.DarkGray,
                                disabledContainerColor = CustomColors.White,
                                disabledTextColor = CustomColors.DarkGray,
                            ),
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                            ),
                            enabled = isSetting
                        )
                    }
                    if(isSetting || passportExpiry.isNotEmpty()) {
                        TextField(
                            value = passportExpiry,
                            onValueChange = setPassportExpiry,
                            leadingIcon = {
                                Text(
                                    text = "여권 만료일",
                                    modifier = Modifier.padding(end = 16.dp),
                                    color = CustomColors.Black,
                                    fontSize = 16.sp
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = CustomColors.White,
                                focusedTextColor = CustomColors.DarkGray,
                                unfocusedContainerColor = CustomColors.White,
                                unfocusedTextColor = CustomColors.DarkGray,
                                disabledContainerColor = CustomColors.White,
                                disabledTextColor = CustomColors.DarkGray,
                            ),
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                            ),
                            enabled = isSetting
                        )
                    }
                }

            }

            // 이름 변경 팝업창
            if(isNameSetting) {
                ProfileUsernameDialog(
                    name = name,
                    setName = setName,
                    setIsNameSetting = setIsNameSetting,
                    onDismiss = { setIsNameSetting(false) },
                )
            }

            // 여권 확인 버튼
        }
    }
}