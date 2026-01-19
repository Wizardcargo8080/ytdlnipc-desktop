package com.deniscerri.ytdl.util

import java.awt.Desktop
import java.net.URI

/**
 * PLATFORM-SPECIFIC: Desktop implementation of UI utilities
 */
object DesktopUiUtil {
    fun openLink(url: String) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(URI(url))
        }
    }
}
