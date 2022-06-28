package com.ComposeDemoApp.viewmodel

import com.ComposeDemoApp.core.uI.BaseViewModel
import com.ComposeDemoApp.data.remote.model.response.ErrorModel
import com.ComposeDemoApp.data.remote.model.response.Photos.PhotosListResponceItem
import com.ComposeDemoApp.data.remote.model.response.Product.Product
import com.ComposeDemoApp.data.remote.model.response.Product.ProductListResponse
import com.ComposeDemoApp.network.ApiException
import com.ComposeDemoApp.repository.DemoRepository
import com.ComposeDemoApp.util.extensionFunction.mutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by JeeteshSurana.
 */

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val repository: DemoRepository/*,
    private val context: Application*/
) : BaseViewModel() {
    var productList = mutableLiveData<ProductListResponse>()
    var productDetails = mutableLiveData<Product>()
    var photosList = mutableLiveData<List<PhotosListResponceItem>>()

    var isLoading = mutableLiveData(false)

/*    private val _getUserData: MutableLiveData<List<ProductListResponse>> =
        MutableLiveData<List<ProductListResponse>>()
    var getUserData: LiveData<List<ProductListResponse>> = _getUserData*/

    suspend fun getProducts() {
        try {
            isLoading.postValue(true)
            productList.postValue(repository.getProducts())
        } catch (e: ApiException) {
            mError.value = e.message?.let { ErrorModel(it, e.errno, e.code) }
        }
    }

    suspend fun getProductsDetails(id: String) {
        try {
            isLoading.postValue(true)
            productDetails.postValue(repository.getProductsDetails(id))
        } catch (e: ApiException) {
            mError.value = e.message?.let { ErrorModel(it, e.errno, e.code) }
        }
    }

    suspend fun getPhotos() {
        try {
            isLoading.postValue(true)
            photosList.postValue(repository.getPhotos())
        } catch (e: ApiException) {
            mError.value = e.message?.let { ErrorModel(it, e.errno, e.code) }
        }
    }
}