package com.example.theworldaroundus.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import com.example.theworldaroundus.R
import com.example.theworldaroundus.data.Country
import com.example.theworldaroundus.ui.theme.BackgroundColor
import com.example.theworldaroundus.ui.theme.TheWorldAroundUsTheme
import com.example.theworldaroundus.ui.theme.Typography
import com.example.theworldaroundus.ui.theme.WhiteColor
import com.example.theworldaroundus.utils.ACTION_BAR_SIZE_DP
import com.example.theworldaroundus.utils.ScreenState
import com.example.theworldaroundus.utils.commonImageLoader
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheWorldAroundUsTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = BackgroundColor
                ) { innerPadding ->
                    MainContent(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel(factory = MainViewModelFactory()),
                    )
                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, viewModel: MainViewModel) {

    val screenState by viewModel.screenState.collectAsState()

    Box(modifier = modifier) {

        BackgroundNet()

        Column(modifier = Modifier) {

            ActionBarMain(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ACTION_BAR_SIZE_DP.dp)
            )

            Box(modifier = Modifier.weight(1f)) {
                when (screenState) {
                    ScreenState.IDE -> {}
                    ScreenState.LOADING -> {
                        ContentLoading(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.5f)
                        )
                    }

                    ScreenState.SUCCESS -> {
                        ContentSuccess(mainViewModel = viewModel)
                    }

                    ScreenState.EMPTY -> {
                        ContentEmpty(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.5f)
                        )
                    }

                    ScreenState.ERROR -> {
                        ContentError(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.5f)
                        )
                    }
                }


            }
        }
    }

}

@Composable
fun ActionBarMain(modifier: Modifier = Modifier) {

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .size(48.dp),
                painter = rememberAsyncImagePainter(model = R.drawable.logo_icon),
                contentDescription = "",
                contentScale = ContentScale.Inside
            )
            Text(
                text = stringResource(R.string.world_around_us),
                style = Typography.bodyLarge.copy(
                    color = WhiteColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        Spacer(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(1.dp)
                .clip(
                    CircleShape
                )
                .background(color = WhiteColor)
        )
    }

}

@Composable
fun ContentSuccess(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {

    val itemCountries by mainViewModel.itemCountries.collectAsState()

    if (itemCountries.isNullOrEmpty()) return

    LazyColumn(
        modifier = modifier,
        state = rememberLazyListState(),
        contentPadding = PaddingValues(all = 16.dp)
    ) {
        items(itemCountries!!) { item ->

            ContentItemCountry(item)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}

@Composable
fun ContentItemCountry(item: Country) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(percent = 10))
            .background(color = WhiteColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .size(45.dp)
                .border(width = 1.dp, color = Color.Yellow, shape = CircleShape)
                .clip(CircleShape)
                .background(color = Color.LightGray),
            model = item.flags?.svg ?: item.flags?.png ?: "",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            imageLoader = commonImageLoader,
        )

        Column(
            modifier = Modifier
                .padding(end = 8.dp)
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = item.name?.common ?: "",
                style = Typography.titleMedium.copy(
                    color = BackgroundColor, fontSize = 14.sp
                ), fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (!item.flags?.alt.isNullOrBlank()){
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = item.flags?.alt ?: "",
                    style = Typography.titleMedium.copy(
                        color = BackgroundColor, fontSize = 12.sp
                    ), fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ContentEmpty(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_icon),
            contentDescription = "",
            contentScale = ContentScale.Inside
        )

        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = stringResource(R.string.empty_result),
            style = Typography.titleMedium.copy(
                color = WhiteColor, fontSize = 14.sp
            ), fontWeight = FontWeight.Normal
        )
    }

}

@Composable
fun ContentError(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.error_icon),
            contentDescription = "",
            contentScale = ContentScale.Inside
        )

        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = stringResource(R.string.error),
            style = Typography.titleMedium.copy(
                color = WhiteColor, fontSize = 14.sp
            ), fontWeight = FontWeight.Normal
        )
    }

}

@Composable
fun ContentLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = WhiteColor, modifier = Modifier.size(48.dp), strokeWidth = 3.dp
        )
    }

}

@Composable
fun BackgroundNet(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(), contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            painter = painterResource(id = R.drawable.background_net),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}