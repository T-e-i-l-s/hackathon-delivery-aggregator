package com.team.feature_auth.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.team.feature_auth.R

@Composable
fun AvatarPicker(avatarUri: Uri?, onImageClick: () -> Unit) {
    avatarUri?.let {
        Image(
            painter = rememberAsyncImagePainter(it),
            contentDescription = null,
            modifier = Modifier
                .shadow(
                    elevation = 32.dp,
                    shape = CircleShape,
                    clip = false,
                    ambientColor = MaterialTheme.colorScheme.onBackground,
                    spotColor = MaterialTheme.colorScheme.onBackground
                )
                .size(160.dp)
                .clip(CircleShape)
                .clickable { onImageClick() }
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.onSurface.copy(0.2f),
                    CircleShape
                ),
            contentScale = ContentScale.Crop
        )
    } ?: Box {
        Image(
            painter = painterResource(R.drawable.avatar),
            contentDescription = null,
            modifier = Modifier
                .shadow(
                    elevation = 32.dp,
                    shape = CircleShape,
                    clip = false,
                    ambientColor = MaterialTheme.colorScheme.onBackground,
                    spotColor = MaterialTheme.colorScheme.onBackground
                )
                .align(Alignment.Center)
                .size(160.dp)
                .clip(CircleShape)
                .clickable { onImageClick() }
        )

        Icon(
            painter = painterResource(R.drawable.plus),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f),
            modifier = Modifier
                .align(Alignment.Center)
                .zIndex(1f)
                .size(160.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background.copy(0.7f))
                .padding(45.dp)
        )
    }
}