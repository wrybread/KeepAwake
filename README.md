# KeepAwake
This prevents a Chromecast with Google TV from going into standby mode after 4 hours, since there's currently a bug with Chromecasts that puts them to into standby after 4 hours no matter what setting you have.

It works by simulating a touch of the Chromecast UI every 15 minutes.

The usual workaround to this problem is to enable "stay awake" in developer options, but that prevents the screensaver from running, and I'm using the screensaver.

To use, just sideload the app and run it once and it'll continue running in the background, even through a reboot. To disable it just uninstall it.


