package com.restro.mealdose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.restro.mealdose.screen.SplashScreen
import com.restro.mealdose.ui.theme.MealDoseTheme
import com.restro.mealdose.ui.theme.SplashTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SplashTheme {
                val context = LocalContext.current
                LaunchedEffect(key1 = true) {
                    delay(3000)
                    context.startActivity(Intent(context, MainActivity::class.java))
                    finish()
                }
                SplashScreen()
            }
        }
    }
}

