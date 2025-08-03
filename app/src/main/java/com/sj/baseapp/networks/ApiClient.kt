package com.sj.hrm.network

import com.sj.baseapp.networks.ApiInterface
import com.sj.baseapp.utils.Constants
import com.sj.baseapp.utils.UserSharedPreference
import com.squareup.moshi.Moshi
import kotlin.collections.iterator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiClient {
    companion object {
        @Volatile
        private var retrofit: Retrofit? = null

        @Volatile
        private var apiInterface: ApiInterface? = null

        private fun buildClient(
            headers: Map<String, String> = mapOf(),
            userSharedPreference: UserSharedPreference
        ): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")

                for (header in headers) {
                    requestBuilder.addHeader(header.key, header.value)
                }

                if (userSharedPreference?.getToken() != null) {
                    requestBuilder.addHeader(
                        "Authorization",
                        userSharedPreference.getToken().toString()
                    )
                }

                val request = requestBuilder.build()

                chain.proceed(request)
            }
            .build()

        fun getRetrofit(
            moshi: Moshi,
            userSharedPreference: UserSharedPreference,
            headers: Map<String, String> = mapOf()
        ): Retrofit = retrofit ?: synchronized(this) {
            retrofit ?: Retrofit.Builder()
                .client(buildClient(headers, userSharedPreference = userSharedPreference))
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(Constants.BASE_URL) // Dynamically set base URL
                .build()
        }

        fun getApiInterface(
            moshi: Moshi,
            userSharedPreference: UserSharedPreference
        ): ApiInterface = apiInterface ?: synchronized(this) {
            getRetrofit(
                moshi = moshi,
                userSharedPreference = userSharedPreference
            ).create(ApiInterface::class.java)
        }

        fun resetRetrofit() {
            retrofit = null
            apiInterface = null
        }
    }
}
