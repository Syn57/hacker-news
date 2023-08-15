package com.ddb.hackernews

import android.app.Application
import com.ddb.hackernews.core.di.databaseModule
import com.ddb.hackernews.core.di.networkModule
import com.ddb.hackernews.core.di.repositoryModule
import com.ddb.hackernews.di.useCaseModule
import com.ddb.hackernews.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}