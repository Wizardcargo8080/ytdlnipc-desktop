package com.deniscerri.ytdl.util

import java.util.Locale

/**
 * CORE: Extension functions extracted from Android Extensions.kt
 * Platform-agnostic string/URL utilities
 */

fun String.isYoutubeURL(): Boolean {
    return this.contains("youtube.com") || this.contains("youtu.be")
}

fun String.isYoutubeWatchVideosURL(): Boolean {
    return this.contains("youtube.com/watch_videos")
}

fun String.isURL(): Boolean {
    return this.startsWith("http://") || this.startsWith("https://")
}

fun String.getIDFromYoutubeURL(): String? {
    val patterns = listOf(
        "(?:youtube\\.com/watch\\?v=|youtu\\.be/)([a-zA-Z0-9_-]{11})".toRegex(),
        "youtube\\.com/shorts/([a-zA-Z0-9_-]{11})".toRegex(),
        "youtube\\.com/embed/([a-zA-Z0-9_-]{11})".toRegex(),
        "youtube\\.com/v/([a-zA-Z0-9_-]{11})".toRegex()
    )
    
    for (pattern in patterns) {
        val match = pattern.find(this)
        if (match != null) {
            return match.groupValues[1]
        }
    }
    return null
}

fun Int.toStringDuration(locale: Locale): String {
    if (this < 0) return ""
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    
    return if (hours > 0) {
        String.format(locale, "%d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format(locale, "%d:%02d", minutes, seconds)
    }
}

fun Long.toStringDuration(locale: Locale): String {
    return this.toInt().toStringDuration(locale)
}

fun List<String>.toListString(): String {
    return this.joinToString(", ")
}
