package com.composeDemoApp.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.composeDemoApp.viewmodel.DemoViewModel

@Composable
fun PostListData(
    viewModel: DemoViewModel = viewModel(),
    onProductClick: (Int) -> Unit,
) {
    val data = viewModel.productList.observeAsState().value
    val isLoading = viewModel.isLoading.observeAsState(false).value
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color.DarkGray else Color.LightGray

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.primary)
                    .statusBarsPadding()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Products List",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = colorScheme.onPrimary
                )
            }

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                data?.products?.takeIf { it.isNotEmpty() }?.let { productList ->
                    LazyColumn(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        items(productList.size) { index ->
                            ItemProduct(productList[index], onProductClick)
                        }
                    }
                }
            }
        }
    }
}