package com.composeDemoApp.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.composeDemoApp.ui.composable.BottomNavigation
import com.composeDemoApp.ui.navigation.NavController
import com.composeDemoApp.ui.theme.ComposeWithBaseStructureTheme
import com.composeDemoApp.util.Constant
import com.composeDemoApp.util.PreferenceProvider
import com.composeDemoApp.viewmodel.DemoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: DemoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeWithBaseStructureTheme {
                val navController = rememberNavController()
                val snackBarHostState = remember { SnackbarHostState() }

                Scaffold(
                    bottomBar = {
                        BottomNavigation(navController = navController)
                    },
                    contentWindowInsets = androidx.compose.foundation.layout.WindowInsets.navigationBars,
                    snackbarHost = {
                        SnackbarHost(snackBarHostState)
                    },
                ) { padding ->
                    NavController(
                        navController = navController,
                        padding = padding,
                        homeViewModel = homeViewModel
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            PreferenceProvider(this@MainActivity).putString("baseUrl", Constant.baseURL)
            homeViewModel.getProducts()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeWithBaseStructureTheme {
        // Preview content
    }
}
