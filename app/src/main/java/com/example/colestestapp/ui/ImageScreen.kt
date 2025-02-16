package com.example.colestestapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.colestestapp.R

@Composable
fun LargeImageView(navController: NavController, imageUrl: String, imageDescription: String) {
    Box(
        Modifier
            .wrapContentSize()
            .background(color = colorResource(R.color.alpha_black))
    ) {
        AsyncImage(
            model = imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.Center),
            contentDescription = imageDescription,
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = "< Go Back",
            color = Color.White,
            modifier = Modifier
                .padding(start = 20.dp, top = 50.dp)
                .clickable { navController.popBackStack() },
            fontSize = 20.sp,
            fontWeight = FontWeight(600)
        )
    }
}