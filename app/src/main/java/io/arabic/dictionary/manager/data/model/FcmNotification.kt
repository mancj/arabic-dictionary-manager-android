package io.arabic.dictionary.manager.data.model

data class FcmNotificationRequest(
    val notification: FcmNotification,
    val to: String?,
    val registration_ids: List<String>?,
    val data: Map<String, String?>,
    val priority: FcmPriority? = FcmPriority.HIGH
)

data class FcmNotification(
    val title: String,
    val sound: String = "default"
)

enum class FcmPriority {
    HIGH,
    LOW,
    MEDIUM
}