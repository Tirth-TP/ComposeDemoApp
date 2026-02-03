package com.composeDemoApp.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.composeDemoApp.R
import com.composeDemoApp.data.Product.Product
import com.composeDemoApp.ui.theme.ComposeWithBaseStructureTheme
import com.composeDemoApp.viewmodel.DemoViewModel

@Composable
fun ProductList(
    viewModel: DemoViewModel = viewModel(),
    onProductClick: (Int) -> Unit,
) {
    val data = viewModel.productList.observeAsState().value
    val isLoading = viewModel.isLoading.observeAsState(false).value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        CommonToolbar(
            title = stringResource(R.string.products_list),
            isBackVisible = false
        )

        val error = viewModel.mError.observeAsState().value

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = error.message,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        } else {
            data?.products?.takeIf { it.isNotEmpty() }?.let { productList ->
                LazyColumn(
                    modifier = Modifier.padding(10.dp)
                ) {
                    items(
                        items = productList,
                        key = { it.id }
                    ) { product ->
                        ItemProduct(product, onProductClick)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
    val mockProducts = listOf(
        Product(
            id = 1,
            title = "iPhone 14 Pro",
            description = "Apple iPhone 14 Pro with A16 Bionic chip and ProMotion display.",
            price = 1299.0,
            discountPercentage = 10.5,
            rating = 4.8,
            stock = 25,
            brand = "Apple",
            category = "smartphones",
            thumbnail = "https://dummyjson.com/image/i/products/1/thumbnail.jpg",
            images = listOf(
                "https://dummyjson.com/image/i/products/1/1.jpg",
                "https://dummyjson.com/image/i/products/1/2.jpg"
            )
        ),
        Product(
            id = 2,
            title = "Samsung Galaxy S23",
            description = "Samsung Galaxy S23 with Dynamic AMOLED display and Snapdragon processor.",
            price = 999.0,
            discountPercentage = 12.0,
            rating = 4.6,
            stock = 40,
            brand = "Samsung",
            category = "smartphones",
            thumbnail = "https://dummyjson.com/image/i/products/2/thumbnail.jpg",
            images = listOf(
                "https://dummyjson.com/image/i/products/2/1.jpg",
                "https://dummyjson.com/image/i/products/2/2.jpg"
            )
        ),
        Product(
            id = 3,
            title = "Sony WH-1000XM5",
            description = "Industry leading noise cancelling wireless headphones.",
            price = 399.0,
            discountPercentage = 15.0,
            rating = 4.7,
            stock = 18,
            brand = "Sony",
            category = "audio",
            thumbnail = "https://dummyjson.com/image/i/products/3/thumbnail.jpg",
            images = listOf(
                "https://dummyjson.com/image/i/products/3/1.jpg"
            )
        ),
        Product(
            id = 4,
            title = "Nike Air Max 270",
            description = "Comfortable and stylish sneakers for everyday wear.",
            price = 180.0,
            discountPercentage = 20.0,
            rating = 4.5,
            stock = 60,
            brand = "Nike",
            category = "footwear",
            thumbnail = "https://dummyjson.com/image/i/products/4/thumbnail.jpg",
            images = listOf(
                "https://dummyjson.com/image/i/products/4/1.jpg",
                "https://dummyjson.com/image/i/products/4/2.jpg"
            )
        )
    )

    ComposeWithBaseStructureTheme {
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            items(mockProducts.size) { index ->
                ItemProduct(product = mockProducts[index], onClick = {})
            }
        }
    }
}
