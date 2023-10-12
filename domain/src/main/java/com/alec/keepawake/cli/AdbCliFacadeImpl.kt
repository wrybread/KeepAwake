package com.wrybread.keepawake.cli

class AdbCliFacadeImpl(
    private val adbCommandRunner: AdbCommandRunner,
): AdbCliFacade {

    override fun cli(command: String) {
        adbCommandRunner.executeCli(command = command)
    }

    override fun touchScreen(x: Int, y: Int) {
        cli("input tap $x $y")
        cli("/system/bin/input tap $x $y")
        //cli("shell input tap $x $y")
        //cli("adb shell input tap $x $y")
    }
}