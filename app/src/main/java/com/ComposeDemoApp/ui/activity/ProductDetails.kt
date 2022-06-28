package com.ComposeDemoApp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.ComposeDemoApp.ui.theme.ComposeWithBaseStructureTheme
import com.ComposeDemoApp.ui.theme.Purple500
import com.ComposeDemoApp.viewmodel.DemoViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@AndroidEntryPoint
class ProductDetails : ComponentActivity() {
    private val homeViewModel: DemoViewModel by viewModels()
    lateinit var productId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        productId = extras?.get("productId").toString()
        setContent {
            ComposeWithBaseStructureTheme {
                ProductDetailsCall(homeViewModel)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            homeViewModel.getProductsDetails(productId)
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun ProductDetailsCall(
    viewModel: DemoViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val data = viewModel.productDetails.observeAsState().value

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        var json = Gson().toJson(viewModel.productList.value)
        Log.e("TAG", "PostListData: Gson $json")
        Log.e("TAG", "PostListData: ViewModel $viewModel")
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
                        text = "Product Details",
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
                    Log.e("TAG", "PostListData: before lazy column called")
                    if (viewModel.productDetails.value != null) {
                        ProductDetailsView(data!!)
                    }
                }
            }
        }
    }
}