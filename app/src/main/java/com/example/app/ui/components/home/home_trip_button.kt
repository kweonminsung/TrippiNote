package com.example.app.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.R
import com.example.app.ui.theme.CustomColors

@Composable
fun HomeTripButton(
    title: String,
    subtitle: String? = null,
    imageId: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(color = CustomColors.White)
            .padding(12.dp)
            .width(280.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sample_trip),
                    contentDescription = "",
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .background(color = CustomColors.White)
                        .shadow(
                            elevation = 1.dp,
                            shape = RoundedCornerShape(8.dp),
                        )
                        .clickable(onClick = onClick)
                )
                Column(
                    modifier = Modifier
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        color = CustomColors.Black,
                    )
                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            color = CustomColors.Gray,
                            modifier = Modifier.padding(top = 4.dp),
                        )
                    }
                }
            }
            Image(
                painter = painterResource(id = R.drawable.button_rightarrow),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}