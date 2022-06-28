package com.ComposeDemoApp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ComposeDemoApp.ui.activity.PhotosList
import com.ComposeDemoApp.ui.activity.PostListData
import com.ComposeDemoApp.ui.activity.ToDoList
import com.ComposeDemoApp.viewmodel.DemoViewModel

/**
 * Created by Tirth Patel.
 */

@OptIn(ExperimentalMaterialApi::class)
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
        modifier = Modifier.padding(paddingValues = padding),

        builder = {

            // route : Home
            composable("home") {
                PostListData(homeViewModel)
            }

            // route : search
            composable("gallery") {
                 PhotosList(homeViewModel)
            }

            // route : profile
            composable("todo") {
                ToDoList()

            }
        })
}