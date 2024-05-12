package com.example.theworldaroundus.detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.theworldaroundus.R
import com.example.theworldaroundus.data.Country
import com.example.theworldaroundus.main.MainContent
import com.example.theworldaroundus.main.MainViewModelFactory
import com.example.theworldaroundus.ui.theme.BackgroundColor

@Composable
fun DetailScreen(
    country: Country?,
    onBackClick: () -> Unit
) {
    Log.d("abc","country name : ${country?.name}")
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundColor
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)) {
                    Image(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 16.dp)
                            .size(48.dp)
                            .clip(CircleShape)
                            .clickable {
                                onBackClick()
                            }
                            .padding(12.dp),
                        painter = rememberAsyncImagePainter(model = R.drawable.back_white_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Inside
                    )
                }
            }
        }
    }
}