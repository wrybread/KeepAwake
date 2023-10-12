package com.wrybread.keepawake.cli

interface AdbCommandRunner {
    fun executeCli(command: String)
}