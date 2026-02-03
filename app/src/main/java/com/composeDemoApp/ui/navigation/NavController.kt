package com.composeDemoApp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.composeDemoApp.ui.composable.GalleryScreen
import com.composeDemoApp.ui.composable.ProductDetails
import com.composeDemoApp.ui.composable.ProductList
import com.composeDemoApp.ui.composable.ToDoScreen
import com.composeDemoApp.viewmodel.DemoViewModel

/**
 * Created by Tirth Patel.
 */

@Composable
fun NavController(
    navController: NavHostController,
    padding: PaddingValues,
    homeViewModel: DemoViewModel,
    onBottomBarVisibilityChanged: (Boolean) -> Unit,
) {

    NavHost(
        navController = navController,

        // set the start destination as home
        startDestination = "home",

        // Set the padding provided by scaffold
        modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),

        builder = {

            // route : Home
            composable("home") {
                LaunchedEffect(Unit) {
                    onBottomBarVisibilityChanged(true)
                }
                val onProductClick = remember(navController) {
                    { productId: Int -> navController.navigate("details/$productId") }
                }
                ProductList(homeViewModel, onProductClick = onProductClick)
            }

            // route : gallery
            composable("gallery") {
                LaunchedEffect(Unit) {
                    onBottomBarVisibilityChanged(true)
                }
                GalleryScreen(homeViewModel)
            }

            // route : todos
            composable("todo") {
                LaunchedEffect(Unit) {
                    onBottomBarVisibilityChanged(true)
                }
                ToDoScreen()

            }

            composable(
                "details/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                LaunchedEffect(Unit) {
                    onBottomBarVisibilityChanged(false)
                }
                val productId =
                    backStackEntry.arguments?.getString("productId") ?: return@composable
                val onBackClick = remember(navController) {
                    {
                        navController.popBackStack()
                        Unit
                    }
                }
                ProductDetails(
                    productId = productId,
                    viewModel = homeViewModel,
                    onBackClick = onBackClick
                )
            }
        }
    )
}