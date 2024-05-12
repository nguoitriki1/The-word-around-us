package com.example.theworldaroundus.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.theworldaroundus.R
import com.example.theworldaroundus.data.Country
import com.example.theworldaroundus.detail.DetailScreen
import com.example.theworldaroundus.ui.theme.BackgroundColor
import com.example.theworldaroundus.ui.theme.TheWorldAroundUsTheme
import com.example.theworldaroundus.ui.theme.Typography
import com.example.theworldaroundus.ui.theme.WhiteColor
import com.example.theworldaroundus.utils.ACTION_BAR_SIZE_DP
import com.example.theworldaroundus.utils.ARGUMENT_DETAIL_SCREEN
import com.example.theworldaroundus.utils.DETAIL_SCREEN_ROUTE
import com.example.theworldaroundus.utils.MAIN_SCREEN_ROUTE
import com.example.theworldaroundus.utils.ScreenState
import com.example.theworldaroundus.utils.commonImageLoader
import com.example.theworldaroundus.utils.toCountry
import com.example.theworldaroundus.utils.toJson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheWorldAroundUsTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = MAIN_SCREEN_ROUTE) {
                    composable(route = MAIN_SCREEN_ROUTE) {
                        MainScreen {
                            navController.navigate("$DETAIL_SCREEN_ROUTE?$ARGUMENT_DETAIL_SCREEN=${it.toJson()}") {
                                restoreState = true
                                launchSingleTop = true
                            }
                        }
                    }

                    composable(
                        route = "$DETAIL_SCREEN_ROUTE?$ARGUMENT_DETAIL_SCREEN={$ARGUMENT_DETAIL_SCREEN}",
                        arguments = listOf(
                            navArgument(ARGUMENT_DETAIL_SCREEN) { type = NavType.StringType },
                        )
                    ) {
                        val countryString = it.arguments?.getString(ARGUMENT_DETAIL_SCREEN) ?: ""


                        DetailScreen(country = countryString.toCountry(),onBackClick = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}
