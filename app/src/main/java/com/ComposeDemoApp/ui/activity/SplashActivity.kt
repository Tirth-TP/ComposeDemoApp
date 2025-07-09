package com.ComposeDemoApp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.ComposeDemoApp.core.uI.BaseActivity
import com.ComposeDemoApp.ui.theme.ComposeWithBaseStructureTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeWithBaseStructureTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Cyan) {
                    lifecycleScope.launch {
                        delay(2000)
                        intent = Intent(this@SplashActivity, MainActivity::class.java).apply {
                            intent.extras?.let { putExtras(it) }
                        }
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}