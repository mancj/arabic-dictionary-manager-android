package io.arabic.dictionary.manager

import android.app.Application
import io.arabic.dictionary.di.AppComponent
import io.arabic.dictionary.di.ContextModule
import io.arabic.dictionary.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }

}