package com.ComposeDemoApp.repository

import android.content.Context
import com.ComposeDemoApp.data.remote.model.response.Photos.PhotosListResponceItem
import com.ComposeDemoApp.data.remote.model.response.Product.Product
import com.ComposeDemoApp.data.remote.model.response.Product.ProductListResponse
import com.ComposeDemoApp.network.ApiRestService
import com.ComposeDemoApp.network.SafeApiRequest
import com.ComposeDemoApp.util.Resource
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
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }
        return Resource.Success(response)
    }

    suspend fun getProducts(): ProductListResponse {
        return apiRequest { api.getProducts() }
    }

    suspend fun getProductsDetails(id: String): Product {
        return apiRequest { api.getProductsDetails(id) }
    }

    suspend fun getPhotos(): List<PhotosListResponceItem> {
        return apiRequest { api.getPhotos() }
    }
}