package com.sj.baseapp.networks

import android.content.Context
import com.sj.baseapp.utils.HeaderTime
import com.sj.baseapp.utils.Utils
import java.net.HttpURLConnection
import kotlinx.coroutines.CancellationException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

object SafeApiRequest {
    suspend fun <T : Any?> apiRequest(
        context: Context,
        call: suspend () -> Response<T>
    ): T? {
        try {
            if (!Utils.isConnectedToInternet(context.applicationContext)) {
                throw ApiException("No internet connection!")
            }

            val response = call.invoke()

            if (response.isSuccessful &&
                response.code() >= HttpURLConnection.HTTP_OK &&
                response.code() < HttpURLConnection.HTTP_MULT_CHOICE
            ) {
                HeaderTime.currentTime = response.headers()["Date"] ?: ""
                return response.body()
            } else {
                val error = response.errorBody()?.string()

                val message = StringBuilder()

                error?.let {
                    try {
                        message.append(JSONObject(it).getString("message"))
                    } catch (e: JSONException) {
                        /* no-op */
                    }
                }

                if (message.isNotEmpty()) {
                    message.append("\n")
                }

                message.append("${response.message()} (${response.code()})")

                //    Timber.e("ApiException: $message")

                throw ApiException(message.toString())
            }
        } catch (e: CancellationException) {
            e.printStackTrace()

            throw e
        } catch (e: Exception) {
            e.printStackTrace()

            throw ApiException(e.message ?: "Unknown Error!")
        }
    }
}
