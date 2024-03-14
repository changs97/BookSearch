package com.test.android.booksearch.data.source.remote

import com.test.android.booksearch.BuildConfig
import com.test.android.booksearch.data.BookApiModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface BookService {
    @GET("v1/search/book.json")
    suspend fun getBooks(
        @Query("query") query: String, @Query("display") display: Int = 100
    ): BookApiModel

    companion object {
        private const val BASE_URL = "https://openapi.naver.com/"

        fun create(): BookService {
            val requestInterceptor = Interceptor { chain ->
                with(chain) {
                    val newRequest =
                        request().newBuilder().addHeader("X-Naver-Client-Id", BuildConfig.CLIENT_ID)
                            .addHeader("X-Naver-Client-Secret", BuildConfig.CLIENT_SECRET).build()

                    proceed(newRequest)
                }
            }

            val logger = HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }

            val client = OkHttpClient.Builder().readTimeout(5000, TimeUnit.MILLISECONDS)
                .connectTimeout(5000, TimeUnit.MILLISECONDS).addInterceptor(logger)
                .addNetworkInterceptor(requestInterceptor).build()

            return Retrofit.Builder().baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(BookService::class.java)
        }
    }
}
