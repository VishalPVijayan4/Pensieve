package com.vishalpvijayan.pensieve

import android.app.Application
import com.vishalpvijayan.pensieve.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PensieveApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PensieveApp)
            modules(appModule)
        }
    }
}
