package io.arabic.dictionary.manager.ui.screen.dailyarticle

import com.arellomobile.mvp.InjectViewState
import io.arabic.dictionary.manager.App
import io.arabic.dictionary.manager.data.model.FcmNotification
import io.arabic.dictionary.manager.data.model.FcmNotificationRequest
import io.arabic.dictionary.manager.utils.NetworkConstants
import io.arabic.dictionary.ui.screen.base.BaseMvpPresenter
import io.arabic.dictionary.ui.screen.base.BaseMvpView
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

interface DailyArticleView : BaseMvpView

@InjectViewState
class DailyArticlePresenter : BaseMvpPresenter<DailyArticleView>() {
    @Inject
    lateinit var httpClient: HttpClient

    init {
        App.appComponent.inject(this)
    }

    fun sendPushNotification(
        notificationTitle: String,
        arabicText: String,
        translation: String,
        articleId: String,
        shortText: String,
        debug: Boolean
    ) =
        safeLaunch(blockUi = true) {
            httpClient.post<ByteArray>(NetworkConstants.Url.URL_FCM_SEND_NOTIFICATION) {
                header("Authorization", NetworkConstants.FCM_API_KEY)
                contentType(ContentType.Application.Json)
                val shortText = if (shortText.isEmpty()) null else shortText
                body = buildNotificationRequest(notificationTitle, arabicText, translation, articleId, shortText, debug)
            }
        }

    private fun buildNotificationRequest(
        notificationTitle: String,
        arabicText: String,
        translation: String,
        articleId: String,
        shortText: String?,
        debug: Boolean
    ): FcmNotificationRequest {
        val registrationIds = if (debug) NetworkConstants.FCM_DEBUG_REGISTRATION_IDS else null
        val topic = if (!debug) NetworkConstants.FCM_TOPIC_COMMON else null
        val data = mapOf(
            "target" to NetworkConstants.FCM_TARGET_DAILY_ARTICLE,
            "arabic" to arabicText,
            "translation" to translation,
            "articleId" to articleId,
            "shortDescription" to shortText
        )

        return FcmNotificationRequest(
            FcmNotification(notificationTitle),
            topic,
            registrationIds,
            data
        )
    }

}