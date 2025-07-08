package com.example.app.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.R
import com.example.app.ui.theme.CustomColors

@Composable
fun ProfileCard(
    name: String,
    email: String,
    image_id: String? = null,
    modifier: Modifier = Modifier
) {
    Row() {
        Image(
            painter = painterResource(id = R.drawable.sample_trip),
            contentDescription = "",
            modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                )
                .background(CustomColors.White),
            contentScale = ContentScale.Crop
        )
        Column() {
            Text(
                text = name,
                modifier = Modifier.padding(start = 8.dp),
                color = CustomColors.Black,
                fontSize = 16.sp
            )
            Text(
                text = email,
                modifier = Modifier.padding(start = 8.dp),
                color = CustomColors.Gray,
                fontSize = 14.sp
            )
        }
        Image(
            painter = painterResource(id = R.drawable.button_rightarrow),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
    }
}