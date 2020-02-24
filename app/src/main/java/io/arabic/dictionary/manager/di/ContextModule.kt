package io.arabic.dictionary.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.arabic.dictionary.manager.App
import io.arabic.dictionary.manager.utils.Constants
import javax.inject.Singleton

@Module
class ContextModule(val app: App) {

    @Provides
    @Singleton
    fun sharedPreferences(): SharedPreferences {
        return app.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun app() = app

}