package com.ComposeDemoApp.network

import android.content.Context
import com.ComposeDemoApp.util.Constant
import com.ComposeDemoApp.util.PreferenceProvider
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URISyntaxException
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Tirth Patel.
 */

@Singleton
class HostSelectionInterceptor @Inject constructor(var context: Context) :
    Interceptor {
    @Volatile
    private var host = "".toHttpUrlOrNull()
    fun setHostBaseUrl() {
        host = PreferenceProvider(context).getString("baseUrl", Constant.baseURL).toString()
            .toHttpUrlOrNull()
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        if (host != null) {
            var newUrl: HttpUrl? = null
            try {
                newUrl = request.url.newBuilder()
                    .scheme(host!!.scheme)
                    .host(host!!.toUrl().toURI().host)
                    .build()
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
            assert(newUrl != null)
            request = request.newBuilder()
                .url(newUrl!!)
                .build()
        }
        return chain.proceed(request)
    }

    init {
        setHostBaseUrl()
    }
}