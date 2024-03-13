package com.test.android.booksearch

import android.app.Application
import timber.log.Timber

class BookApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}