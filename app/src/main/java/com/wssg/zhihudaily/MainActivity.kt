package com.wssg.zhihudaily

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.wssg.zhihudaily.page.HomePage
import com.wssg.zhihudaily.page.WebView
import com.wssg.zhihudaily.ui.theme.ZhihuDailyTheme
import com.wssg.zhihudaily.util.RouteConfig
import com.wssg.zhihudaily.util.decode
import com.wssg.zhihudaily.util.encode

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZhihuDailyTheme {
                val navController = rememberAnimatedNavController()
                AnimatedNavHost(
                    navController = navController,
                    startDestination = RouteConfig.HOMEPAGE,
                    enterTransition = { slideInHorizontally { it } },
                    exitTransition = { slideOutHorizontally { it } },
                    popEnterTransition = { slideInHorizontally { it } },
                    popExitTransition = { slideOutHorizontally { it } }
                ) {
                    composable(RouteConfig.HOMEPAGE) {
                        HomePage { url ->
                            navController.navigate("${RouteConfig.WEBVIEW}/${url.encode()}")
                        }
                    }
                    composable(
                        "${RouteConfig.WEBVIEW}/{${RouteConfig.PARAMS_URL}}",
                        arguments = listOf(navArgument(RouteConfig.PARAMS_URL) {
                            type = NavType.StringType
                        })
                    ) {
                        val argument = requireNotNull(it.arguments)
                        WebView(url = argument.getString(RouteConfig.PARAMS_URL)!!.decode())
                    }
                }
            }
        }
    }
}



