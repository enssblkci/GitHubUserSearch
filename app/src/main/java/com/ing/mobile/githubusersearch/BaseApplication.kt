package com.ing.mobile.githubusersearch

import android.app.Application
import androidx.multidex.MultiDex
import com.ing.mobile.githubusersearch.di.userSearchDI
import com.ing.mobile.githubusersearch.network.NetworkModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        startInject()
    }
    private fun startInject() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BaseApplication)
            modules(listOf( userSearchDI,NetworkModules))
        }
    }
}