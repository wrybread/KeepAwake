package com.wrybread.keepawake.cli

interface AdbCliFacade {
    fun cli(command: String)

    fun touchScreen(x: Int, y: Int)
}