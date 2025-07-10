package com.example.app.ui.components.profile

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import coil.compose.AsyncImage
import com.example.app.LocalSession
import com.example.app.R
import com.example.app.R.drawable.sample_profile_image
import com.example.app.ui.pages.album.drawableResToByteArray
import com.example.app.ui.theme.CustomColors
import com.example.app.util.KeyValueStore
import com.example.app.util.ObjectStorage
import com.example.app.util.ObjectStorage.save
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalFoundationApi::class)
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

    val (imageId, setImageId) = remember { mutableStateOf(sessionData.user.image_id) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val tempFile = File(context.cacheDir, "selected_image.jpg")
            inputStream?.use { input ->
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                }
            }
            val file_id = save(context, tempFile.readBytes()).toString()
            setImageId(file_id)

            // 프로필 저장 로직
            sessionData.user.image_id = file_id
            KeyValueStore.saveBulk(context, sessionData)
        }
    }

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
                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = 20.dp,
                        vertical = 12.dp
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

                Box(
                    modifier = Modifier
                        .combinedClickable(
                            onClick = {},
                            onLongClick = {
                                launcher.launch("image/*")
                            }
                        )
                ) {
                    if (imageId != null) {
                        AsyncImage(
                            model = ObjectStorage.read(context, imageId),
                            contentDescription = "Profile Image",
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
                    } else {
                        val sampleImageByteArray =
                            drawableResToByteArray(context, sample_profile_image)
                        AsyncImage(
                            model = sampleImageByteArray,
                            contentDescription = "Sample Profile Image",
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
                    }
                }

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
                            placeholder = {
                                Text(
                                    text = "전화번호를 입력하세요",
                                    color = CustomColors.Gray
                                )
                            },
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
                            placeholder = {
                                Text(
                                    text = "생년월일을 입력하세요",
                                    color = CustomColors.Gray
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
                            placeholder = {
                                Text(
                                    text = "영문 이름을 입력하세요",
                                    color = CustomColors.Gray
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
                            placeholder = {
                                Text(
                                    text = "여권 번호를 입력하세요",
                                    color = CustomColors.Gray
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
                            placeholder = {
                                Text(
                                    text = "여권 만료일을 입력하세요",
                                    color = CustomColors.Gray
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