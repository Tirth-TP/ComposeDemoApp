package com.composeDemoApp.repository

import android.content.Context
import com.composeDemoApp.data.Photos.PhotosListResponseItem
import com.composeDemoApp.data.Product.Product
import com.composeDemoApp.data.Product.ProductListResponse
import com.composeDemoApp.network.ApiRestService
import com.composeDemoApp.network.SafeApiRequest
import com.composeDemoApp.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class DemoRepository @Inject constructor(
    @ApplicationContext context: Context,
    private val api: ApiRestService,
) : SafeApiRequest(context) {

    suspend fun getWeather(): Resource<List<ProductListResponse>> {
        val response = try {
            api.getWeather()
        } catch (e: Exception) {
            return Resource.Error("An unknown error occur: ${e.localizedMessage}")
        }
        return Resource.Success(response)
    }

    suspend fun getProducts(): ProductListResponse {
        return apiRequest { api.getProducts() }
    }

    suspend fun getProductsDetails(id: String): Product {
        return apiRequest { api.getProductsDetails(id) }
    }

    suspend fun getPhotos(): List<PhotosListResponseItem> {
        return apiRequest { api.getPhotos() }
    }
}