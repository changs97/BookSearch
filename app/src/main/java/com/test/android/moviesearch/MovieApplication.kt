package com.test.android.moviesearch

import android.app.Application
import timber.log.Timber

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Application 클래스 사용 이유 공부
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree()) // Timber 사용 이유 공부
        }
    }
}