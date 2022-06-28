package com.ComposeDemoApp.ui.activity

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ComposeDemoApp.ui.theme.Purple500
import com.ComposeDemoApp.viewmodel.DemoViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun PhotosList(
    viewModel: DemoViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val data = viewModel.photosList.observeAsState().value
    scope.launch {
        viewModel.getPhotos()
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
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
                        text = "Gallery",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
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
                    if (viewModel.photosList.value != null) {

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),

                            // content padding
                            contentPadding = PaddingValues(
                                start = 12.dp,
                                top = 16.dp,
                                end = 12.dp,
                                bottom = 16.dp
                            ),
                            content = {
                                data?.size?.let { it1 ->
                                    items(it1) { index ->
                                        Card(
                                            backgroundColor = Color.Gray,
                                            modifier = Modifier
                                                .padding(4.dp)
                                                .fillMaxWidth(),
                                            elevation = 8.dp,
                                        ) {
                                            Image(
                                                painter = rememberAsyncImagePainter(
                                                    data[index].thumbnailUrl
                                                ),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(80.dp)
                                                    .padding(16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}