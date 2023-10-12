package com.wrybread.keepawake.notification

interface NotificationHelper {
    fun createAndShowCommandRunNotification(
        commandExecutedSuccessfully: Boolean,
    )

    fun createNotification(
        content: String,
    )
}