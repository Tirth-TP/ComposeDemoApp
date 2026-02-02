package com.composeDemoApp.network

import android.content.Context
import com.composeDemoApp.data.Photos.PhotosListResponseItem
import com.composeDemoApp.data.Product.Product
import com.composeDemoApp.data.Product.ProductListResponse
import com.composeDemoApp.network.interceptor.HeaderInterceptor
import com.composeDemoApp.network.interceptor.NetworkInterceptor
import com.composeDemoApp.util.Constant
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * Created by Tirth Patel.
 */
lateinit var okHttpBuilder: OkHttpClient.Builder

interface ApiRestService {

    @GET(Constant.PRODUCTS)
    suspend fun getWeather(): List<ProductListResponse>

    @GET(Constant.PRODUCTS)
    suspend fun getProducts(): Response<ProductListResponse>

    @GET("${Constant.PRODUCTS}/{id}")
    suspend fun getProductsDetails(@Path("id") id: String): Response<Product>

    @GET(Constant.PHOTOS_URL)
    suspend fun getPhotos(): Response<List<PhotosListResponseItem>>


    companion object {
        operator fun invoke(
            context: Context,
            networkInterceptor: NetworkInterceptor,
            headerInterceptor: HeaderInterceptor
        ): ApiRestService {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val cacheSize = 20 * 1024 * 1024L // 20 MB

            fun provideCache(): Cache? {
                var cache: Cache? = null
                try {
                    cache = Cache(File(context.cacheDir, "Cache_directory"), cacheSize)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return cache
            }

            okHttpBuilder = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .cache(provideCache())
                .addInterceptor(networkInterceptor)
                .addInterceptor(logging)
                .addInterceptor(headerInterceptor)


            return Retrofit.Builder()
                .baseUrl(Constant.BASEURL)
                .client(okHttpBuilder.build())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRestService::class.java)
        }
    }
}
