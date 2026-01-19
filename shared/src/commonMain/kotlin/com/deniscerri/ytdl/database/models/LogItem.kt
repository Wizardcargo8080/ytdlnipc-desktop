package com.deniscerri.ytdl.database.models

import kotlinx.serialization.Serializable

@Serializable
data class LogItem(
    var id: Long,
    var title: String,
    var content: String,
    var format: Format,
    var downloadType: DownloadType,
    var downloadTime: Long,
)
