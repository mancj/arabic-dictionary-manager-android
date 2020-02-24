package io.arabic.dictionary.ui.screen.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.annotation.MenuRes
import androidx.core.view.isVisible
import com.arellomobile.mvp.MvpAppCompatFragment
import io.arabic.dictionary.manager.R
import io.arabic.dictionary.manager.utils.ext.ui
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseMvpFragment : MvpAppCompatFragment(), BaseMvpView {
    @Suppress("MemberVisibilityCanBePrivate")
    open var fragmentTag: String = javaClass.name
    @MenuRes
    open val menuRes: Int? = null
    private val pendingActions: MutableList<() -> Unit> = mutableListOf()
    private var isBlocked: Boolean = false
        private set
    open val uiProgressLayout: View? get() = view?.findViewById(R.id.progressLayout)
    private var uiBlockingCancelJobs = mutableMapOf<Int, Job?>()

    open val layoutRes: Int? = null

    fun schedulePendingAction(action: () -> Unit) {
        if (view == null) {
            pendingActions.add(action)
        } else {
            action()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(menuRes != null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (layoutRes != null) {
            inflater.inflate(layoutRes!!, container, false)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        for (pendingAction in pendingActions) {
            pendingActions.remove(pendingAction)
            pendingAction()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menuRes?.let {
            inflater.inflate(it, menu)
        }
    }

    override fun getContext(): Context {
        return super.getContext()!!
    }

    override fun onError(throwable: Throwable) {
        throwable.printStackTrace()
    }

    override fun blockUi() {
        uiProgressLayout?.isVisible = true
        isBlocked = true
    }

    override fun unblockUi() {
        uiProgressLayout?.isVisible = false
        isBlocked = false
    }

    override fun onDestroy() {
        super.onDestroy()
        pendingActions.clear()
    }

    @Suppress("unchecked_cast")
    inline fun <reified T : Activity> getParentActivity(): T? {
        return activity as? T
    }

    fun getBaseActivity(): BaseMvpActivity? = activity as? BaseMvpActivity

    fun <T> runUiBlockingAction(target: T, cancelDelay: Long, action: T.() -> Unit) {
        uiBlockingCancelJobs[target.hashCode()]?.cancel()
        ui.launch {
            uiBlockingCancelJobs[target.hashCode()] = coroutineContext[Job]
            delay(cancelDelay)
            action(target)
        }
    }

    fun hideKeyboard() {
        val imm: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        activity?.currentFocus?.windowToken?.let {
            imm.hideSoftInputFromWindow(
                it,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

}

