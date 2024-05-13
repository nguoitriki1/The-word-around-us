package com.example.theworldaroundus.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.theworldaroundus.R
import com.example.theworldaroundus.data.Country
import com.example.theworldaroundus.ui.theme.BackgroundColor
import com.example.theworldaroundus.ui.theme.PurpleColor9167DB
import com.example.theworldaroundus.ui.theme.Typography
import com.example.theworldaroundus.ui.theme.WhiteColor

@Composable
fun DetailScreen(
    country: Country?,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundColor
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Image(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
                    .alpha(0.1f),
                painter = rememberAsyncImagePainter(model = country?.flags?.png),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )

            Column(modifier = Modifier.fillMaxSize()) {
                ActionBarDetail(onBackClick, country)

                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 16.dp, top = 8.dp)
                        .verticalScroll(state = rememberScrollState())
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(3))
                        .background(color = WhiteColor)
                        .padding(horizontal = 16.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .height(200.dp)
                            .align(alignment = Alignment.CenterHorizontally),
                        painter = rememberAsyncImagePainter(model = country?.flags?.png),
                        contentDescription = "",
                        contentScale = ContentScale.FillHeight
                    )
                    ContentCountryDetail(
                        title = stringResource(id = R.string.common_name),
                        info = country?.name?.common ?: ""
                    )

                    ContentCountryDetail(
                        title = stringResource(id = R.string.description),
                        info = country?.flags?.alt ?: ""
                    )

                    ContentCountryDetail(
                        title = stringResource(id = R.string.url_flags_png),
                        info = country?.flags?.png ?: ""
                    )

                    ContentCountryDetail(
                        title = stringResource(id = R.string.url_flags_svg),
                        info = country?.flags?.svg ?: ""
                    )

                    val nativeName = country?.name?.nativeName ?: return@Scaffold

                    nativeName.keys.forEach {
                        Column {
                            Text(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .fillMaxWidth(),
                                text = it,
                                style = Typography.bodyLarge.copy(
                                    color = Color.Red,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            nativeName[it]?.forEach {
                                ContentCountryDetail(it.key, it.value)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ContentCountryDetail(title: String, info: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
            text = title,
            style = Typography.bodyLarge.copy(
                color = PurpleColor9167DB,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
            text = info,
            style = Typography.bodyLarge.copy(
                color = BackgroundColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            ),
        )
    }
}

@Composable
private fun ActionBarDetail(
    onBackClick: () -> Unit,
    country: Country?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
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

        Text(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f),
            text = country?.name?.common ?: "",
            style = Typography.bodyLarge.copy(
                color = WhiteColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}