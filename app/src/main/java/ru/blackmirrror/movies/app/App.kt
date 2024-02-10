package ru.blackmirrror.movies.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import ru.blackmirrror.movies.app.di.appModule
import ru.blackmirrror.movies.app.di.dataModule
import ru.blackmirrror.movies.app.di.domainModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule, domainModule, appModule)
        }
    }
}