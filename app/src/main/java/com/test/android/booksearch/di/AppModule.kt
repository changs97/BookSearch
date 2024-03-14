package com.test.android.booksearch.di


import com.test.android.booksearch.BuildConfig
import com.test.android.booksearch.data.source.remote.ApiHelper
import com.test.android.booksearch.data.source.remote.ApiHelperImpl
import com.test.android.booksearch.data.source.remote.BookService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {
    companion object {
        @Provides
        @Singleton
        fun providesRetrofit(): Retrofit {
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

            return Retrofit.Builder().baseUrl("https://openapi.naver.com/").client(client)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }

        @Provides
        @Singleton
        fun providesApiService(retrofit: Retrofit): BookService {
            return retrofit.create(BookService::class.java)
        }
    }

    @Binds
    @Singleton
    abstract fun bindsApiService(apiHelper: ApiHelperImpl): ApiHelper
}