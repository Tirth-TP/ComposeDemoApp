package com.composeDemoApp.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
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
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            homeViewModel.isLoading.value ?: false
        }

        setContent {
            ComposeWithBaseStructureTheme {
                val navController = rememberNavController()
                val snackBarHostState = remember { SnackbarHostState() }

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // State to control bottom bar visibility
                var showBottomBar by remember { mutableStateOf(true) }

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigation(
                                navController = navController,
                                currentRoute = currentRoute
                            )
                        }
                    },
                    contentWindowInsets = androidx.compose.foundation.layout.WindowInsets.navigationBars,
                    snackbarHost = {
                        SnackbarHost(snackBarHostState)
                    },
                ) { padding ->
                    Surface(
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        NavController(
                            navController = navController,
                            padding = padding,
                            homeViewModel = homeViewModel,
                            onBottomBarVisibilityChanged = { isVisible ->
                                showBottomBar = isVisible
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            PreferenceProvider(this@MainActivity).putString("baseUrl", Constant.BASEURL)
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
