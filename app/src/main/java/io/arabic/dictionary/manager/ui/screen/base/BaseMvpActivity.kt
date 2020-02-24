package io.arabic.dictionary.ui.screen.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.core.view.isVisible
import com.arellomobile.mvp.MvpAppCompatActivity
import io.arabic.dictionary.manager.BuildConfig
import io.arabic.dictionary.manager.R
import kotlinx.android.synthetic.main.layout_progress.*


abstract class BaseMvpActivity : MvpAppCompatActivity(), BaseMvpView {
    @MenuRes
    open val menuRes: Int? = null
    @ForFragment
    var onBackPressedListener: (() -> Boolean)? = null
    @ForFragment
    var onHomeItemClickListener: (() -> Boolean)? = null
    abstract val presenter: BaseMvpPresenter<*>

    var isBlocked: Boolean = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG && requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            throw IllegalStateException(
                "Please set portrait orientation " +
                        "for this activity in the AndroidManifest.xml"
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuRes?.let {
            menuInflater.inflate(it, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onError(throwable: Throwable) {
        unblockUi()
        Toast.makeText(this, R.string.common_error, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home && onHomeItemClickListener?.invoke() != true) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @Suppress("unused")
    inline fun <reified T : BaseMvpFragment> findFragment(): T? {
        return findFragment(T::class.java.name)
    }

    override fun blockUi() {
        if (!isBlocked) {
            progressLayout?.isVisible = true
            isBlocked = true
        }
    }

    override fun unblockUi() {
        progressLayout?.isVisible = false
        isBlocked = false
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : BaseMvpFragment> findFragment(fragmentTag: String): T? {
        return supportFragmentManager.findFragmentByTag(fragmentTag) as? T
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : androidx.fragment.app.Fragment> findFragmentById(@IdRes id: Int): T? {
        return supportFragmentManager.findFragmentById(id) as? T
    }

    fun hideKeyboard() {
        val imm: InputMethodManager = getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        currentFocus?.windowToken?.let {
            imm.hideSoftInputFromWindow(it, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    fun showKeyboard(view: View) {
        val imm: InputMethodManager = getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onBackPressed() {
        if (onBackPressedListener?.invoke() != true) {
            super.onBackPressed()
        }
    }

}