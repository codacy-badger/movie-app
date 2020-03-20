package br.com.jeramovies

import android.app.Application
import br.com.jeramovies.presentation.di.*
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class JeraMoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        startKoin {
            androidLogger()
            androidContext(this@JeraMoviesApp)
            modules(
                listOf(
                    dbModule,
                    apiModule,
                    repositoryModule,
                    viewModelModule,
                    appModule
                )
            )
        }
    }
}