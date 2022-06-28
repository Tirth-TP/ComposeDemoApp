package com.ComposeDemoApp.data.remote.model.response.Product


data class ProductListResponse(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)