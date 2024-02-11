package ru.blackmirrror.movies.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"
    //private const val API_KEY = "5e2c6535-8ee4-40ae-bbee-756fc448d82b"
    private const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(makeLoggingInterceptor())
        .addInterceptor(makeApiKeyInterceptor())
        .build()

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    private fun makeApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("x-api-key", API_KEY)
                .build()
            chain.proceed(newRequest)
        }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun create(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
