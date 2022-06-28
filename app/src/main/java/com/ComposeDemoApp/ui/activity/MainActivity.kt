package com.ComposeDemoApp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ComposeDemoApp.R
import com.ComposeDemoApp.core.uI.BaseActivity
import com.ComposeDemoApp.ui.navigation.BottomNavItem
import com.ComposeDemoApp.ui.navigation.NavController
import com.ComposeDemoApp.ui.theme.ComposeWithBaseStructureTheme
import com.ComposeDemoApp.ui.theme.Purple500
import com.ComposeDemoApp.util.Constant
import com.ComposeDemoApp.util.PreferenceProvider
import com.ComposeDemoApp.viewmodel.DemoViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val homeViewModel: DemoViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeWithBaseStructureTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                PostListData(homeViewModel)
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
                            NavController(navController = navController, padding = padding, homeViewModel)
                        }
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


@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun PostListData(
    viewModel: DemoViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val data = viewModel.productList.observeAsState().value
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        var json = Gson().toJson(viewModel.productList.value)
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState
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
                        text = "Products List",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }

                if (viewModel.isLoading.value == false) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
                if (viewModel.isLoading.value == true) {
                    if (viewModel.productList.value != null && !data?.products.isNullOrEmpty()) {
                        LazyColumn(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            items(data?.products!!.size) { index ->
                                ProductListCall(data?.products!![index])
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Gallery,
        BottomNavItem.Todo
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.teal_200),
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeWithBaseStructureTheme {
    }
}