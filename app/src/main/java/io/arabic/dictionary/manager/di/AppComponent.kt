package io.arabic.dictionary.di

import dagger.Component
import io.arabic.dictionary.manager.di.NetworkModule
import io.arabic.dictionary.manager.ui.screen.dailyarticle.DailyArticlePresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(p: DailyArticlePresenter)

}