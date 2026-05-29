package com.example.muzukasii

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


enum class Navigation(
    val route: String, val icon: ImageVector, val label: String, val contentDescription: String
) {

    HOME("home", Icons.Default.Home, "ホーム", "Home tab"), SEARCH(
        "search",
        Icons.Default.Search,
        "検索",
        "com.example.muzukasii.Search tab"
    ),

}

object AppRoutes {
    const val RECOMMENDED = "recommended"
}

//ナビゲーションバーと画面遷移をくっつけてるとこ
@Composable
fun NavigationBarExample(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val startNavigation = Navigation.HOME
    var selectedNavigation by rememberSaveable { mutableIntStateOf(startNavigation.ordinal) }

    Scaffold(
        bottomBar = {
            Surface(
                border = BorderStroke(1.dp, Color.LightGray),
            ) {
                NavigationBar(
                    windowInsets = NavigationBarDefaults.windowInsets,
                    containerColor = Color.White,
                ) {
                    Navigation.entries.forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            selected = selectedNavigation == index, onClick = {
                                selectedNavigation = index
                                navController.navigate(navigationItem.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }, icon = {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Icon(
                                        imageVector = navigationItem.icon,
                                        contentDescription = navigationItem.contentDescription,
                                        modifier = Modifier.size(35.dp)
                                    )
                                }
                            }, label = null, colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFFC00000),

                                unselectedIconColor = Color.Gray,
                                indicatorColor = Color.Transparent,
                            )
                        )
                    }
                }
            }

        }) { innerPadding ->
        AppNavHost(
            navController = navController,
            startNavigation = startNavigation,
            innerPadding = innerPadding,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


//画面遷移
@Composable
fun AppNavHost(
    navController: NavHostController,
    startNavigation: Navigation,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = startNavigation.route,
        modifier = modifier.padding(innerPadding)
    ) {
        composable(Navigation.SEARCH.route) {
            Search()
        }
        composable(Navigation.HOME.route) {
            HomeScreen(onBannerClick = { navController.navigate(AppRoutes.RECOMMENDED) })
        }
        composable(AppRoutes.RECOMMENDED) {
            RecommendedScreen(
                onBack = { navController.popBackStack() },
                onCardClick = { url ->
                    navController.navigate("webview/${Uri.encode(url)}")
                })
        }
        composable(
            "webview/{url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            WebViewScreen(url = url, onBack = { navController.popBackStack() })
        }
    }
}

