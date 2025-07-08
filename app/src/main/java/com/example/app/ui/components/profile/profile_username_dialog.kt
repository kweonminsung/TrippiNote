package com.example.app.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.app.LocalSession
import com.example.app.ui.theme.CustomColors
import com.example.app.util.KeyValueStore

@Composable
fun ProfileUsernameDialog(
    name: String,
    setName: (String) -> Unit,
    setIsNameSetting: (Boolean) -> Unit,
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current
    val sessionData = LocalSession.current.value

    val (newName, setNewName) = remember { mutableStateOf(name) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = true
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = CustomColors.White,
                    shape = RoundedCornerShape(8.dp)
                ),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "이름 변경",
                    fontSize = 20.sp,
                    color = CustomColors.Black
                )

                TextField(
                    value = newName,
                    onValueChange = { input ->
                        setNewName(input)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = CustomColors.White,
                        focusedTextColor = CustomColors.Black,
                        unfocusedContainerColor = CustomColors.White,
                        unfocusedTextColor = CustomColors.Black,
                    ),
                    modifier = Modifier.padding(16.dp),
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    ),
                )

                Button(
                    onClick = {
                        setIsNameSetting(false)
                        setName(newName)
                        sessionData.user.name = newName
                        KeyValueStore.saveBulk(context, sessionData)
                        onDismiss()
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
                    onClick = {
                        setIsNameSetting(false)
                        onDismiss()
                    },
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