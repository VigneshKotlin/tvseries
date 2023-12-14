package com.populartv.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.populartv.app.model.TVSeries
import com.populartv.app.utils.AppConstants

@Composable
fun DetailsScreen(tvSeriesModel: TVSeries?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .wrapContentSize(Alignment.TopCenter)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth(1f)) {
            val maxHeight = this.maxHeight
            val topHeight: Dp = maxHeight * 25/100
            val imageTopPadding = topHeight/ 2
            Column(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .height(topHeight)
                    .background(Color.LightGray)
            ) {

            }
            if (tvSeriesModel!!.poster_path != null) {
                var isImageLoading by remember { mutableStateOf(false) }

                val painter = rememberAsyncImagePainter(
                    model = AppConstants.IMAGE_BASE_URL + tvSeriesModel.poster_path,
                )

                isImageLoading = when(painter.state) {
                    is AsyncImagePainter.State.Loading -> true
                    else -> false
                }

                Box (
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 10.dp, top = imageTopPadding )
                            .height(250.dp)
                            .width(150.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        painter = painter,
                        contentDescription = "Poster Image",
                        contentScale = ContentScale.FillBounds,
                    )

                    if (isImageLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(horizontal = 6.dp, vertical = 3.dp),
                            color = MaterialTheme.colors.primary,
                        )
                    }
                }
            }
        }

        Text(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 8.dp),
        text = tvSeriesModel!!.original_name + " | Release : "+tvSeriesModel!!.first_air_date,
        fontSize = 20.sp,
        color = Color.White
        )
        Text(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 8.dp),
            text = tvSeriesModel!!.overview,
            fontSize = 16.sp,
            color = Color.LightGray
        )

    }
}