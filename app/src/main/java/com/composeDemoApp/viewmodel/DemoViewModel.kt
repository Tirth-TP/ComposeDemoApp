package com.composeDemoApp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.composeDemoApp.data.remote.model.response.ErrorModel
import com.composeDemoApp.data.remote.model.response.Photos.PhotosListResponceItem
import com.composeDemoApp.data.remote.model.response.Product.Product
import com.composeDemoApp.data.remote.model.response.Product.ProductListResponse
import com.composeDemoApp.network.ApiException
import com.composeDemoApp.repository.DemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val repository: DemoRepository
) : ViewModel() {
    val mError = MutableLiveData<ErrorModel>()
    var productList = MutableLiveData<ProductListResponse>()
    var productDetails = MutableLiveData<Product>()
    var photosList = MutableLiveData<List<PhotosListResponceItem>>()

    var isLoading = MutableLiveData(false)

    fun getProducts() {
        viewModelScope.launch {
            try {
                isLoading.postValue(true)
                productList.postValue(repository.getProducts())
            } catch (e: ApiException) {
                mError.value = e.message?.let { ErrorModel(it, e.errno, e.code) }
            }
        }
    }

    fun getProductsDetails(id: String) {
        viewModelScope.launch {
            try {
                isLoading.postValue(true)
                productDetails.postValue(repository.getProductsDetails(id))
            } catch (e: ApiException) {
                mError.value = e.message?.let { ErrorModel(it, e.errno, e.code) }
            }
        }
    }

    fun getPhotos() {
        viewModelScope.launch {
            try {
                isLoading.postValue(true)
                photosList.postValue(repository.getPhotos())
            } catch (e: ApiException) {
                mError.value = e.message?.let { ErrorModel(it, e.errno, e.code) }
            }
        }
    }
}