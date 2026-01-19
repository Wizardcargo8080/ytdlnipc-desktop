package com.deniscerri.ytdl.database.models

import kotlinx.serialization.Serializable

@Serializable
data class DownloadItem(
    var id: Long,
    var url: String,
    var title: String,
    var author: String,
    var thumb: String,
    var duration: String,
    var type: DownloadType,
    var format: Format,
    var container: String = "Default",
    var downloadSections: String = "",
    var allFormats: MutableList<Format>,
    var downloadPath: String,
    var website: String,
    val downloadSize: String,
    var playlistTitle: String,
    val audioPreferences: AudioPreferences,
    val videoPreferences: VideoPreferences,
    var extraCommands: String = "",
    var customFileNameTemplate: String,
    var SaveThumb: Boolean,
    var status: String = "Queued",
    var downloadStartTime: Long = 0,
    var logID: Long?,
    var playlistURL: String? = "",
    var playlistIndex: Int? = null,
    var incognito: Boolean = false,
    var availableSubtitles: List<String> = listOf(),
    var rowNumber: Int = 0
)
