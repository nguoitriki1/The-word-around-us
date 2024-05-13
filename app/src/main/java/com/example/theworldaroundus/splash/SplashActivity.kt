package com.example.theworldaroundus.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.theworldaroundus.R
import com.example.theworldaroundus.main.MainActivity
import com.example.theworldaroundus.ui.theme.TheWorldAroundUsTheme
import com.example.theworldaroundus.ui.theme.Typography
import com.example.theworldaroundus.ui.theme.WhiteColor
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheWorldAroundUsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    LaunchedEffect(Unit) {
                        delay(2000)
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }

                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = rememberAsyncImagePainter(model = R.drawable.background_splash),
                            contentDescription = "",
                            contentScale = ContentScale.FillHeight
                        )

                        Text(
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .padding(horizontal = 16.dp)
                                .padding(innerPadding)
                                .align(Alignment.BottomCenter),
                            text = stringResource(R.string.welcome_to_the_world_around_us)+"!",
                            textAlign = TextAlign.Center,
                            style = Typography.titleMedium.copy(
                                color = WhiteColor,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
    }
}