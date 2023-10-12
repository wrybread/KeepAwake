package com.wrybread.keepawake.worker

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun Context.scheduleKeepAwakeWorker() {
    Log.i("KeepAwake", "scheduleKeepAwakeWorker: scheduling")

    WorkManager.getInstance(this)
        .enqueueUniquePeriodicWork(
            "Keep Awake Command Runner",
            ExistingPeriodicWorkPolicy.KEEP,
            PeriodicWorkRequestBuilder<ChromeCastKeepAwakeWorker>(
                repeatInterval = 15, //%% was 15
                repeatIntervalTimeUnit = TimeUnit.MINUTES,
                flexTimeInterval = 5,
                flexTimeIntervalUnit = TimeUnit.MINUTES,
            ).build()
        )

    Log.i("KeepAwake", "scheduleKeepAwakeWorker: scheduled")
}