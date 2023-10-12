package com.wrybread.keepawake.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.wrybread.keepawake.worker.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class NotificationHelperImpl(
    private val context: Context,
    private val uiCoroutine: CoroutineScope,
): NotificationHelper {

    private val notificationManager: NotificationManager? by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
    }

    override fun createAndShowCommandRunNotification(
        commandExecutedSuccessfully: Boolean,
    ) {
        createNotification(
            content = context.getString(
                R.string.adb_command_executed,
                commandExecutedSuccessfully.toString()
            ),
        )
    }

    override fun createNotification(
        content: String,
    ) {
        uiCoroutine.launch {

            createNotificationChannel(
                context = context,
            )

            val builder = NotificationCompat.Builder(
                context,
                context.getString(R.string.keep_awake_channel_id)
            )
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_MIN)

            with(
                NotificationManagerCompat.from(context)
            ) {
                // notificationId is a unique int for each notification that you must define.
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return@launch
                }

                notify(
                    Random(21).nextInt(),
                    builder.build()
                )
            }
        }
    }

    private fun createNotificationChannel(
        context: Context,
    ) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.keep_awake_channel_id)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(
                context.getString(R.string.keep_awake_channel_id),
                name,
                importance,
            ).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            notificationManager?.createNotificationChannel(channel)
        }
    }
}