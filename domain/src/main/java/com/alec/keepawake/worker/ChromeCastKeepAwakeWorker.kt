package com.wrybread.keepawake.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.wrybread.keepawake.cli.AdbCliFacade
import com.wrybread.keepawake.cli.AdbCliFacadeImpl
import com.wrybread.keepawake.cli.AdbCommandRunnerImpl
import com.wrybread.keepawake.notification.NotificationHelper
import com.wrybread.keepawake.notification.NotificationHelperImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ChromeCastKeepAwakeWorker(
    context: Context,
    workParams: WorkerParameters,
): CoroutineWorker(
    context,
    workParams,
) {

    private val adbCliFacade: AdbCliFacade = AdbCliFacadeImpl(
        adbCommandRunner = AdbCommandRunnerImpl(
            adbCoroutine = CoroutineScope( Dispatchers.IO + Job()),
        )
    )
    private val notificationHelper: NotificationHelper = NotificationHelperImpl(
        context = context,
        uiCoroutine = CoroutineScope( Dispatchers.Default + Job()),
    )


    override suspend fun doWork(): Result {
        Log.i(TAG, "doWork: Running ADB.")

        val adbSuccess = try {
            executeTouchCommand()
            true
        } catch (ex: Exception) {
            false
        }

        Log.i(TAG, "doWork: ADB Success = $adbSuccess.")

        Log.i(TAG, "doWork: Building Notification.")

        buildAndShowNotification(
            commandExecutedSuccessfully = adbSuccess,
        )

        Log.i(TAG, "doWork: No Exception during notification.")

        return if (adbSuccess) {
            Result.success()
        } else {
            Result.failure()
        }
    }

    private fun executeTouchCommand() {
        adbCliFacade.touchScreen(ADB_TOUCH_CMD_DEFAULT_X_AXIS, ADB_TOUCH_CMD_DEFAULT_Y_AXIS)
    }

    private fun buildAndShowNotification(
        commandExecutedSuccessfully: Boolean,
    ) {
        notificationHelper.createAndShowCommandRunNotification(
            commandExecutedSuccessfully = commandExecutedSuccessfully,
        )
    }

    companion object {
        private const val TAG = "ChromeCastKeepAwakeWork"

        private const val ADB_TOUCH_CMD_DEFAULT_X_AXIS = 100
        private const val ADB_TOUCH_CMD_DEFAULT_Y_AXIS = 100
    }
}