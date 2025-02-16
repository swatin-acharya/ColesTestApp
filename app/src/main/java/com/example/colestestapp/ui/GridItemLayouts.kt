package com.example.colestestapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.colestestapp.R

@Composable
fun GridPhotoItemWithPhotoHeaderAndText(
    photoUrl: String,
    photoAlt: String,
    header: String,
    descriptionText: String,
    isCurrentRecipe: Boolean,
    onclick: () -> Unit
) {
    Column(
        Modifier
            .wrapContentSize()
            .padding(10.dp)
            .clickable { onclick() }
    ) {
        Box(Modifier.wrapContentSize()) {
            AsyncImage(
                model = photoUrl,
                contentDescription = photoAlt,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            val alphaValue = if (isCurrentRecipe) 1f else 0f
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(color = colorResource(R.color.alpha_black))
                    .alpha(alphaValue),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.current),
                    style = currentTextStyle,
                    modifier = Modifier.alpha(alphaValue)
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = header,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = redHeadline,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = descriptionText,
            maxLines = 2,
            style = descriptionBlack,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}