package com.deniscerri.ytdl.util

import com.deniscerri.ytdl.database.models.Format

/**
 * CORE logic: Ported from Android FormatUtil.kt
 * Provides generic audio/video formats used when yt-dlp extraction is limited or skipped
 */
object FormatUtil {

    fun getGenericAudioFormats(): MutableList<Format> {
        val formats = mutableListOf<Format>()
        
        // Android equivalent used R.array.audio_containers
        // We replicate the list of generic formats created in FormatUtil.kt (Android)
        
        listOf("m4a", "mp3", "ogg", "opus", "webm", "wav", "flac").forEach { container ->
            formats.add(
                Format(
                    format_id = container,
                    format_note = "ytdlnisgeneric",
                    container = container,
                    vcodec = "none",
                    acodec = container,
                    filesize = 0,
                    tbr = ""
                )
            )
        }
        
        return formats
    }

    fun getGenericVideoFormats(): MutableList<Format> {
        val formats = mutableListOf<Format>()
        
        listOf("mp4", "mkv", "webm", "flv").forEach { container ->
            formats.add(
                Format(
                    format_id = container,
                    format_note = "ytdlnisgeneric",
                    container = container,
                    vcodec = container,
                    acodec = "none",
                    filesize = 0,
                    tbr = ""
                )
            )
        }
        
        return formats
    }
}
