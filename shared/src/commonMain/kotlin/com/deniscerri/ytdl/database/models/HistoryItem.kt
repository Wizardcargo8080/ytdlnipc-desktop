package com.deniscerri.ytdl.database.models

import kotlinx.serialization.Serializable

@Serializable
data class HistoryItem(
    var id: Long,
    val url: String,
    val title: String,
    val author: String,
    val duration: String,
    val thumb: String,
    val type: DownloadType,
    val time: Long,
    val downloadPath: List<String>,
    val website: String,
    val format: Format,
    val filesize: Long = 0,
    val downloadId: Long,
    val command: String = ""
)
