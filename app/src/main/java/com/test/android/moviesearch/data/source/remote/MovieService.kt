package com.test.android.moviesearch.data.source.remote

import com.test.android.moviesearch.BuildConfig
import com.test.android.moviesearch.data.MovieApiModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

// Retrofit 이렇게 사용하는 이유와 내부 로직 공부
interface MovieService {
    @GET("v1/search/movie.json")
    suspend fun getMovies(
        @Query("query") query: String,
    ): MovieApiModel

    // companion object, object, class 공부, 각각의 사용 이유도
    companion object {
        private const val BASE_URL = "https://openapi.naver.com/"

        fun create(): MovieService {
            val requestInterceptor = Interceptor { chain ->
                with(chain) {
                    val newRequest = request().newBuilder() // 키 숨기는 이유 공부
                        .addHeader("X-Naver-Client-Id", BuildConfig.CLIENT_ID)
                        .addHeader("X-Naver-Client-Secret", BuildConfig.CLIENT_SECRET)
                        .build()

                    proceed(newRequest)
                }
            }

            val logger =
                HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG) {
                            HttpLoggingInterceptor.Level.BODY
                        } else {
                            HttpLoggingInterceptor.Level.NONE
                        }
                }

            // OkHttpClient 사용 이유 공부
            val client = OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .addInterceptor(logger)
                .addNetworkInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client) // Gson 공부, Converter 공부
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService::class.java)
        }
    }
}
