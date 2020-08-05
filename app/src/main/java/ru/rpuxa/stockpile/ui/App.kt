package ru.rpuxa.stockpile.ui

import android.app.Application
import ru.rpuxa.stockpile.di.AppComponent
import ru.rpuxa.stockpile.di.AppModule
import ru.rpuxa.stockpile.di.DaggerAppComponent

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var component: AppComponent
    }
}