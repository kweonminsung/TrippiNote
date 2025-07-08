package com.example.app.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.app.R
import com.example.app.R.drawable.sample_profile_image
import com.example.app.ui.pages.album.drawableResToByteArray
import com.example.app.ui.theme.CustomColors
import com.example.app.util.ObjectStorage

@Composable
fun ProfileCard(
    name: String,
    email: String,
    onClick: () -> Unit,
    imageId: String? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(8.dp),
            )
            .background(
                color = CustomColors.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if(imageId != null) {
            AsyncImage(
                model = ObjectStorage.read(context, imageId),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .width(84.dp)
                    .height(84.dp)
                    .shadow(
                        elevation = 1.dp,
                        shape = RoundedCornerShape(42.dp),
                    )
                    .background(CustomColors.White),
                contentScale = ContentScale.Crop
            )
        } else {
            val sampleImageByteArray = drawableResToByteArray(context, sample_profile_image)
            AsyncImage(
                model = sampleImageByteArray,
                contentDescription = "Sample Profile Image",
                modifier = Modifier
                    .width(84.dp)
                    .height(84.dp)
                    .shadow(
                        elevation = 1.dp,
                        shape = RoundedCornerShape(42.dp),
                    )
                    .background(CustomColors.White),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = name,
                color = CustomColors.Black,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = email,
                color = CustomColors.Gray,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Image(
            painter = painterResource(id = R.drawable.button_rightarrow),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
    }
}