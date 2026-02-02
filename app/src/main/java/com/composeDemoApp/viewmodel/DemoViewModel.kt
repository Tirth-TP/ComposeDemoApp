package com.composeDemoApp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composeDemoApp.data.ErrorModel
import com.composeDemoApp.data.Photos.PhotosListResponseItem
import com.composeDemoApp.data.Product.Product
import com.composeDemoApp.data.Product.ProductListResponse
import com.composeDemoApp.network.ApiException
import com.composeDemoApp.repository.DemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val repository: DemoRepository,
) : ViewModel() {
    private val _mError = MutableLiveData<ErrorModel>()
    val mError: LiveData<ErrorModel> = _mError

    private val _productList = MutableLiveData<ProductListResponse>()
    val productList: LiveData<ProductListResponse> = _productList

    private val _productDetails = MutableLiveData<Product>()
    val productDetails: LiveData<Product> = _productDetails

    private val _photosList = MutableLiveData<List<PhotosListResponseItem>>()
    val photosList: LiveData<List<PhotosListResponseItem>> = _photosList

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getProducts() {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                _productList.postValue(repository.getProducts())
            } catch (e: ApiException) {
                _mError.value = e.message?.let { ErrorModel(it, e.errno, e.code) }
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun getProductsDetails(id: String) {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                _productDetails.postValue(repository.getProductsDetails(id))
            } catch (e: ApiException) {
                _mError.value = e.message?.let { ErrorModel(it, e.errno, e.code) }
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun getPhotos() {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                _photosList.postValue(repository.getPhotos())
            } catch (e: ApiException) {
                _mError.value = e.message?.let { ErrorModel(it, e.errno, e.code) }
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}