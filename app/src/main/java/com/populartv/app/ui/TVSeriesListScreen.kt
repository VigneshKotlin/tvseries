package com.populartv.app.ui

import android.R.attr.actionBarSize
import android.R.attr.maxLines
import android.view.View
import android.view.ViewTreeObserver
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import com.populartv.app.utils.AppConstants
import com.populartv.app.viewmodel.TVSeriesViewModel

val ROUTE_TVSERIES = "tvseries-route?tvseries={tvseries}"
@Composable
fun TVSeriesListScreen(state: MutableState<TextFieldValue>, navController: NavController) {
    val tvSeriesViewModel = hiltViewModel<TVSeriesViewModel>()
    var popularTvSeries = tvSeriesViewModel.getPopularTVSeries().collectAsLazyPagingItems()
    val scaffoldState = rememberScaffoldState()
    val searchText = state.value.text
    var search by remember { mutableStateOf(false) }
    search = searchText.isNotEmpty()
    if(search){
        popularTvSeries = tvSeriesViewModel.getPopularTVSeriesByName(searchText).collectAsLazyPagingItems()
    }else{
        popularTvSeries = tvSeriesViewModel.getPopularTVSeriesByName(searchText).collectAsLazyPagingItems()
    }
    LazyColumn(modifier = Modifier.background(Color.Gray)){

        items(
            items =popularTvSeries,
        ){ tvSeries ->
            tvSeries?.let {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxWidth()
                        .clickable {
                            val tvSeriesJson = Gson().toJson(it)
                            navController.navigate(
                                ROUTE_TVSERIES.replace("{tvseries}", tvSeriesJson))/*{
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo("main") {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }*/ }
                ) {
                    if (tvSeries.poster_path != null) {
                        var isImageLoading by remember { mutableStateOf(false) }

                        val painter = rememberAsyncImagePainter(
                            model = AppConstants.IMAGE_BASE_URL + tvSeries.poster_path,
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
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                                    .height(115.dp)
                                    .width(77.dp)
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
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 5.dp, horizontal = 8.dp),
                            text = it.original_name + " | Release : "+it.first_air_date,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                        Text(
                            modifier = Modifier
                                .padding(vertical = 5.dp, horizontal = 8.dp),
                            text = it.overview,
                            fontSize = 10.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.LightGray
                        )
                    }

                }
                Divider()
                Divider()
            }
        }

        val loadState = popularTvSeries.loadState.mediator
        item {
            if (loadState?.refresh == LoadState.Loading) {
                Column(
                    modifier = Modifier
                        .fillParentMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = "Refresh Loading"
                    )

                    CircularProgressIndicator(color = MaterialTheme.colors.primary)
                }
            }

            if (loadState?.append == LoadState.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colors.primary)
                }
            }

            if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
                val isPaginatingError = (loadState.append is LoadState.Error) || popularTvSeries.itemCount > 1
                val error = if (loadState.append is LoadState.Error)
                    (loadState.append as LoadState.Error).error
                else
                    (loadState.refresh as LoadState.Error).error

                val modifier = if (isPaginatingError) {
                    Modifier.padding(8.dp)
                } else {
                    Modifier.fillParentMaxSize()
                }
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (!isPaginatingError) {
                        Icon(
                            modifier = Modifier
                                .size(64.dp),
                            imageVector = Icons.Rounded.Warning, contentDescription = null
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = error.message ?: error.toString(),
                        textAlign = TextAlign.Center,
                    )

                    Button(
                        onClick = {
                            popularTvSeries.refresh()
                        },
                        content = {
                            Text(text = "Refresh")
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.Gray,
                        )
                    )
                }
            }
        }
    }
}
