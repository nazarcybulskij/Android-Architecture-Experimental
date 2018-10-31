package my.cybulski.nazar.architecture_experemetal

import android.app.Application
import my.cybulski.nazar.architecture_experemetal.di.appModule
import org.koin.android.ext.android.startKoin

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this,listOf(appModule))
    }

}