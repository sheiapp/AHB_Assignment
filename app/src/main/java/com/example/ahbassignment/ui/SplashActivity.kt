package com.example.ahbassignment.ui

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ahbassignment.KotlinFlowsTheme
import com.example.ahbassignment.R
import com.example.ahbassignment.ui.theme.Route
import com.example.ahbassignment.repository.FirebaseRemoteConfigRepository.Companion.timeOutDelayInMillisecond
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KotlinFlowsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigation()
                }
            }
        }
    }

    @Composable
    fun Navigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Route.splashScreen) {
            composable(Route.splashScreen) {
                SplashScreen(navController)
            }
            composable(Route.mainScreen) {
                MainScreen()
            }
        }
    }

    @Composable
    fun SplashScreen(navController: NavController) {
        val config by remember { mutableStateOf(viewModel.uiState) }
        val scale = remember {
            Animatable(0f)
        }
        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = durationInMillisecond,
                    easing = { OvershootInterpolator(5f).getInterpolation(it) })
            )
            delay(timeOutDelayInMillisecond)
            if (config.value.maintenanceStatus != null && config.value.maintenanceStatus != true)
                navController.navigate(Route.mainScreen)
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = resources.getString(R.string.splash_text),
                style = MaterialTheme.typography.h1,
                modifier = Modifier.scale(scale.value)
            )
            if (config.value.maintenanceStatus != null && config.value.maintenanceStatus == true)
                Banner(
                    bannerText = config.value.bannerText, modifier = Modifier
                        .fillMaxHeight(.3f)
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colors.primary,
                            shape = MaterialTheme.shapes.large
                        )
                        .align(Alignment.TopStart)
                )
        }
    }

    @Composable
    fun MainScreen() {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "main screen",
                color = Color(R.color.splash_text_color),
                style = MaterialTheme.typography.h1
            )
        }
    }

    @Composable
    fun Banner(bannerText: String?, modifier: Modifier) {
        Box(
            modifier = modifier.padding(10.dp),
            contentAlignment = Alignment.Center,
        ) {
            bannerText?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colors.background,
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }

    companion object {
        private const val durationInMillisecond: Int = 5 * 100
    }
}