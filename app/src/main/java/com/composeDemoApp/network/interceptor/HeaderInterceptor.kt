package com.composeDemoApp.network.interceptor

import com.composeDemoApp.BuildConfig
import com.composeDemoApp.util.DeviceUtil
import com.composeDemoApp.util.PreferenceProvider
import okhttp3.*
import okio.Buffer
import okio.BufferedSource
import java.net.HttpURLConnection.HTTP_CLIENT_TIMEOUT

/**
 * Created by Tirth Patel.
 */
class HeaderInterceptor(
    private val deviceUtil: DeviceUtil,
    private val preferenceProvider: PreferenceProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        return try {
            chain.run {
                deviceUtil.getUserAgent()?.let { }

                val builder: Request.Builder = request().newBuilder()


                builder.addHeader("Application-Version", BuildConfig.VERSION_CODE.toString())
                proceed(builder.build())
            }
        } catch (e: Exception) {
            Response.Builder()
                .code(HTTP_CLIENT_TIMEOUT)
                .request(chain.request())
                .body(object : ResponseBody() {
                    override fun contentLength() = 0L
                    override fun contentType(): MediaType? = null
                    override fun source(): BufferedSource = Buffer()
                })
                .message(e.message ?: e.toString())
                .protocol(Protocol.HTTP_1_1)
                .build()
        }
    }
}
