package io.arabic.dictionary.ui.screen.base

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface BaseMvpView : MvpView {

    fun onError(throwable: Throwable)

    fun blockUi()

    fun unblockUi()

}