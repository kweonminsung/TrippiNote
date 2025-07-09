package com.example.app.ui.pages.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.LocalSession
import com.example.app.R
import com.example.app.ui.components.animations.FireworkDialog
import com.example.app.ui.components.profile.ProfileCard
import com.example.app.ui.components.profile.ProfileCountBox
import com.example.app.ui.components.profile.ProfileSpecific
import com.example.app.ui.theme.CustomColors
import com.example.app.util.database.ChecklistRepository
import com.example.app.util.database.MapRepository
import com.example.app.util.database.model

@Composable
fun ProfileTab() {
    val context = LocalContext.current

    val sessionData = LocalSession.current.value
    val username = sessionData.user.name
    val email = sessionData.user.email
    val imageId = sessionData.user.image_id

    val tripsCount = MapRepository.countTrips(context)
    val regionsCount = MapRepository.countRegions(context)

    val (specificOpen, setSpecificOpen) = remember { mutableStateOf(false) }

    if (specificOpen) {
        ProfileSpecific(
            onDismiss = { setSpecificOpen(false) },
        )
    }

    val (checklists, setChecklists) = remember { mutableStateOf(ChecklistRepository.getAllChecklists(context)) }

    val (showFireworks, setShowFireworks) = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = CustomColors.LighterGray
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 30.dp,
                    vertical = 8.dp
                )
        ) {
            ProfileCard(
                name = username,
                email = email,
                onClick = { setSpecificOpen(true) },
                imageId = imageId
            )

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = "여행 상태",
                color = CustomColors.Black,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileCountBox(
                    count = tripsCount,
                    label = "Trips",
                    onClick = {
                        setShowFireworks(true)
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.weight(0.1f))
                ProfileCountBox(
                    count = regionsCount,
                    label = "Regions",
                    onClick = {
                        setShowFireworks(true)
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 1.dp,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(
                        color = CustomColors.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                ) {
                    Text(
                        text = "체크리스트",
                        color = CustomColors.Black,
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    IconButton(
                        onClick = {
                            val newChecklist = model.Checklist(
                                id = -1,
                                done = false,
                                content = ""
                            )
                            ChecklistRepository.createChecklist(context, newChecklist)
                            setChecklists(ChecklistRepository.getAllChecklists(context))
                        },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "추가",
                            tint = CustomColors.Black,
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (checklist in checklists) {
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Checkbox(
                                    checked = checklist.done,
                                    onCheckedChange = { isChecked ->
                                        ChecklistRepository.updateChecklist(context, model.Checklist(
                                            id = checklist.id,
                                            done = isChecked,
                                            content = checklist.content
                                        ))
                                        setChecklists(ChecklistRepository.getAllChecklists(context))
                                    },
                                    modifier = Modifier
                                        .size(24.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = CustomColors.LightGreen,
                                        checkmarkColor = CustomColors.White,
                                    )
                                )
                                BasicTextField(
                                    value = checklist.content,
                                    onValueChange = { newText ->
                                        ChecklistRepository.updateChecklist(context, model.Checklist(
                                            id = checklist.id,
                                            done = checklist.done,
                                            content = newText
                                        ))
                                        setChecklists(ChecklistRepository.getAllChecklists(context))
                                    },
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                    ,
                                    textStyle = TextStyle(
                                        color = CustomColors.Black,
                                        fontSize = 16.sp,
                                        textDecoration = if (checklist.done) TextDecoration.LineThrough else TextDecoration.None
                                    ),
                                    decorationBox = { innerTextField ->
                                        Column {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                            ) {
                                                innerTextField()
                                                Image(
                                                    painter = painterResource(id = R.drawable.all_exit),
                                                    contentDescription = "",
                                                    modifier = Modifier
                                                        .size(10.dp)
                                                        .align(Alignment.CenterEnd)
                                                        .clickable {
                                                            ChecklistRepository.deleteChecklist(context, checklist)
                                                            setChecklists(ChecklistRepository.getAllChecklists(context))
                                                        },
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(1.dp)
                                                    .background(
                                                        color = CustomColors.Gray,
                                                    )
                                            )
                                        }
                                    }

                                )
                            }
                        }
                    }


                }
            }
        }

        if(showFireworks) {
            FireworkDialog(
                onDismissRequest = { setShowFireworks(false) },
                autoDismissMillis = 2000L
            )
        }
    }
}