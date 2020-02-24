package io.arabic.dictionary.manager.ui.screen.dailyarticle

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.textfield.TextInputLayout
import io.arabic.dictionary.manager.R
import io.arabic.dictionary.ui.screen.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_daily_article.*
import kotlinx.android.synthetic.main.include_toolbar.*

class DailyArticleActivity : BaseMvpActivity(), DailyArticleView {
    @InjectPresenter
    override lateinit var presenter: DailyArticlePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_article)
        setSupportActionBar(toolbar)

        confirmButton.setOnClickListener { showConfirmationContainer() }
        editButton.setOnClickListener { showFillContainer() }
        sendButton.setOnClickListener { sendNotification() }
    }

    private fun showConfirmationContainer() {
        if (checkTextEdits()) {
            hideKeyboard()
            confirmationContainer.isVisible = true
            fillContainer.isVisible = false

            arabicTextView.text = arabicTextEdit.text
            notificationTitleView.text = notificationTitleEdit.text
            translationView.text = translationEdit.text
            articleIdView.text = articleIdEdit.text
            shortTextView.text = shortTextEdit.text
        }
    }

    private fun showFillContainer() {
        confirmationContainer.isVisible = false
        fillContainer.isVisible = true
    }

    private fun sendNotification() {
        presenter.sendPushNotification(
            notificationTitleEdit.text.toString(),
            arabicTextEdit.text.toString(),
            translationEdit.text.toString(),
            articleIdEdit.text.toString(),
            shortTextEdit.text.toString(),
            testSwitch.isChecked
        )
    }

    private fun checkTextEdits(): Boolean {
        return fillContainer.children
            .filterIsInstance(TextInputLayout::class.java)
            .takeWhile { it.editText?.id != R.id.shortTextEdit }
            .map {
                it.error = null
                if (it.editText!!.text.isEmpty()) {
                    it.error = getString(R.string.fill_this_field)
                }
                it.editText!!.text.isNotEmpty()
            }
            .all { it }
    }

    override fun onNotificationSend() {
        Toast.makeText(this, "Уведомление успешно отправлено", Toast.LENGTH_SHORT).show()
        finish()
    }
}