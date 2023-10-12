package com.wrybread.keepawake.reboot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.wrybread.keepawake.worker.scheduleKeepAwakeWorker

class WorkManagerStartReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i(TAG, "onReceive: Boot Detected... \nInitiating Work Request...")
        context?.scheduleKeepAwakeWorker()
    }

    companion object {
        private const val TAG = "WorkManagerStartReceive"
    }
}