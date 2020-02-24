package io.arabic.dictionary.manager.utils

object NetworkConstants {

    object Url {
        const val URL_FCM_SEND_NOTIFICATION = "https://fcm.googleapis.com/fcm/send"
    }

    const val FCM_API_KEY =
        "key=AAAAyz0cJ1U:APA91bH8QkS6XMQo4EWsjnKbDiqZaDCxuug85GfMGOo1fr182p5S4V6H0jMkr3O9dcW4rE03pB2prYnx0mbZ6" +
                "_eCW3YGDLGqm5SAmRXTS3DJMsOMQZ1408F_Wa-6o9aRWlmt5jVbDFOG"
    val FCM_DEBUG_REGISTRATION_IDS =
        listOf("ePSJtnpGsHo:APA91bFEzbazY7KKwt_twIJ2kehwHX9ZZuw6C_w4wtDQDQTZS8bbGJG5g5MHSbKj48LeTxkIvrRzT4tjI9M" +
                "-ihpMmfWjXkYHbYXiyI1yzLN87z6KELo4NSVtxONOv0xIT_L-ZO5tRrQ-")
    val FCM_TOPIC_COMMON = "/topics/common"
    val FCM_TARGET_DAILY_ARTICLE = "daily_article"

}