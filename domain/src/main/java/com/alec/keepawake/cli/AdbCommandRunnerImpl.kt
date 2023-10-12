package com.wrybread.keepawake.cli

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class AdbCommandRunnerImpl(
    private val adbCoroutine: CoroutineScope,
): AdbCommandRunner {

    companion object {
        private const val TAG = "keepawakelog"
    }


    override fun executeCli(command: String) {
        adbCoroutine.launch(context = Dispatchers.IO) {
            runtimeApi(
                command = command,
            )
        }
    }

    private fun runtimeApi(command: String) {
        try {
            Log.i(TAG, "Command: $command.")

            val output = StringBuffer()

            val process = Runtime
                .getRuntime()
                .exec(command).also {
                    val reader = BufferedReader(
                        InputStreamReader(it.inputStream)
                    )

                    var read: Int
                    val buffer = CharArray(4096)
                    while (reader.read(buffer).also { read = it } > 0) {
                        output.append(buffer, 0, read)
                    }

                    reader.close()

                    Log.i(TAG, "Command-Output: $output.")
                }

            process.waitFor()
        } catch (exception: Exception) {
            Log.e(TAG, "Command-Error: ${exception.message}.", exception)
        }
    }
}