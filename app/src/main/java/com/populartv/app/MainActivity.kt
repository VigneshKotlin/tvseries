package com.populartv.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.populartv.app.commonui.theme.TVSeriesTheme
import com.populartv.app.model.TVSeries
import com.populartv.app.ui.DetailsScreen
import com.populartv.app.ui.SearchView
import com.populartv.app.ui.TVSeriesListScreen
import com.populartv.app.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TVSeriesTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = { TopBar() },
                        backgroundColor = Color.DarkGray
                    ){ padding -> // We need to pass scaffold's inner padding to content. That's why we use Box.
                        Box(modifier = Modifier.padding(padding)) {
                            Navigation()
                        }
                    }

                }
            }
        }
    }
}
@Composable
fun MainScreen(navController: NavController) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    Column {
       SearchView(textState)
        TVSeriesListScreen(textState, navController)
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val ROUTE_TVSERIES = "tvseries-route?tvseries={tvseries}"
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController = navController)
        }
        composable(ROUTE_TVSERIES)
        {
            /*val tvseries: TVSeries? = navController.previousBackStackEntry?.savedStateHandle?.get("tvseries")
            DetailsScreen(tvSeriesModel = tvseries)*/
            backStackEntry ->
            val tvSeriesJson = backStackEntry.arguments?.getString("tvseries")
            val tvSeries = Gson().fromJson(tvSeriesJson,TVSeries::class.java)
            DetailsScreen(tvSeriesModel = tvSeries)
            }
        }
    }

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = AppConstants.HOME_SCREEN_TITLE, fontSize = 18.sp) },
        backgroundColor = Color.DarkGray,
        contentColor = Color.White
    )
}
