package com.composeDemoApp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.composeDemoApp.ui.activity.PhotosList
import com.composeDemoApp.ui.activity.PostListData
import com.composeDemoApp.ui.activity.ProductDetailsCall
import com.composeDemoApp.ui.activity.ToDoList
import com.composeDemoApp.viewmodel.DemoViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Created by Tirth Patel.
 */

@Composable
fun NavController(
    navController: NavHostController,
    padding: PaddingValues,
    homeViewModel: DemoViewModel
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
                PostListData(homeViewModel) { productId ->
                    navController.navigate("details/$productId")
                }
            }

            // route : search
            composable("gallery") {
                 PhotosList(homeViewModel)
            }

            // route : profile
            composable("todo") {
                ToDoList()

            }

            composable(
                "details/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: return@composable
                ProductDetailsCall(productId = productId)
            }
        })
}