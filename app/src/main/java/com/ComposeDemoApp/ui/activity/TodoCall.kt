package com.ComposeDemoApp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.ComposeDemoApp.core.uI.BaseActivity
import com.ComposeDemoApp.ui.navigation.NavController
import com.ComposeDemoApp.ui.theme.ComposeWithBaseStructureTheme
import com.ComposeDemoApp.ui.theme.Purple500
import com.ComposeDemoApp.viewmodel.DemoViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TodoCall() : BaseActivity() {
    private val homeViewModel: DemoViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ComposeWithBaseStructureTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                PostListData()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    Scaffold(
                        // Bottom navigation
                        bottomBar = {
                            BottomNavigation(navController = navController)
                        }, content = { padding ->
                            // Navhost: where screens are placed
                            NavController(
                                navController = navController,
                                padding = padding,
                                homeViewModel
                            )
                        }
                    )
                }
            }
        }
    }


}


@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun ToDoList() {

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Purple500)
                    .padding(15.dp)
            ) {
                Text(
                    text = "TODO",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement  = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

               Text(
                   text = "Under develpoment",
                   fontWeight = FontWeight.Bold,
                   fontSize = 30.sp,
               )
            }
        }
    }

}

