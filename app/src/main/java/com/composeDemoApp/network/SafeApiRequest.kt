@file:Suppress("BlockingMethodInNonBlockingContext")

package com.composeDemoApp.network

import android.content.Context
import com.composeDemoApp.network.HttpConstants.SOMETHING_WANT_WRONG
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.net.HttpURLConnection

/**
 * Created by Tirth Patel.
 */

abstract class SafeApiRequest(val context: Context) {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        }

        val error = response.errorBody()?.string()
        val (message, errNo) = parseError(error, response.code())

        throw ApiException(message, errNo, response.code())
    }

    private fun parseError(error: String?, responseCode: Int): Pair<String, String?> {
        var message = ""
        var errNo: String? = null

        if (!error.isNullOrBlank()) {
            try {
                val jsonObject = JSONObject(error)
                if (jsonObject.has("errno")) {
                    errNo = jsonObject.getString("errno")
                }
                if (jsonObject.has("message")) {
                    message = jsonObject.getString("message")
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        if (message.isEmpty() && responseCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT) {
            message = SOMETHING_WANT_WRONG
            errNo = HttpURLConnection.HTTP_CLIENT_TIMEOUT.toString()
        }

        return Pair(message, errNo)
    }
}
