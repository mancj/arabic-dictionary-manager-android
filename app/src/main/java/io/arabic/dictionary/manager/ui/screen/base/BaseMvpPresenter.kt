package io.arabic.dictionary.ui.screen.base

import android.content.res.Resources
import com.arellomobile.mvp.MvpPresenter
import io.arabic.dictionary.manager.App
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

abstract class BaseMvpPresenter<V : BaseMvpView> : MvpPresenter<V>() {

    private var disposableJobs: MutableList<Job> = mutableListOf()
    protected val resources: Resources = App.instance.resources
    protected val scope = GlobalScope

    @Suppress("MemberVisibilityCanBePrivate")
    fun safeLaunch(
            coroutineContext: CoroutineContext = Dispatchers.Main,
            blockUi: Boolean = false,
            block: suspend CoroutineScope.() -> Unit
    ): Job {
        return safeLaunch(coroutineContext, blockUi, block, null)
    }

    fun safeLaunch(
            coroutineContext: CoroutineContext = Dispatchers.Main,
            blockUi: Boolean = false,
            block: suspend CoroutineScope.() -> Unit,
            onError: ((Throwable) -> Unit)? = null
    ): Job {
        val job = CoroutineScope(coroutineContext).launch {
            safeLaunchSuspend({
                if (blockUi) viewState.blockUi()
                block(this)
                if (blockUi) viewState.unblockUi()
            }, onError)
        }
        disposableJobs.add(job)
        return job
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected suspend fun safeLaunchSuspend(block: suspend () -> Unit, onError: ((Throwable) -> Unit)? = null) {
        try {
            block()
            disposableJobs.remove(coroutineContext[Job])
        } catch (throwable: Throwable) {
            throwable.printStackTrace()

            if (throwable !is CancellationException) {
                if (onError != null) {
                    onError(throwable)
                } else {
                    viewState?.onError(throwable)
                }
            }
        }
    }

    fun addDisposableJob(job: Job?) {
        job?.let { disposableJobs.add(it) }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun cancelAllJobs() {
        for (job in disposableJobs) {
            job.cancel()
        }
        disposableJobs.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelAllJobs()
    }

}
