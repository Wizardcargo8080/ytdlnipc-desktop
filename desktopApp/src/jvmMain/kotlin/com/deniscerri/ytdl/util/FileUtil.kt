package com.deniscerri.ytdl.util

/**
 * PLATFORM-SPECIFIC: File utility functions for desktop
 */
object FileUtil {
    fun convertFileSize(bytes: Long): String {
        if (bytes <= 0) return "?"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(bytes.toDouble()) / Math.log10(1024.0)).toInt()
        return String.format("%.2f %s", bytes / Math.pow(1024.0, digitGroups.toDouble()), units[digitGroups])
    }
}
