package s.sin.githubrepositories

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import s.sin.githubrepositories.di.appModule
import s.sin.githubrepositories.di.networkModule

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // DI: Koin
        startKoin {
            androidContext(this@CustomApplication)
            modules(listOf(networkModule) + listOf(appModule))
        }

    }
}