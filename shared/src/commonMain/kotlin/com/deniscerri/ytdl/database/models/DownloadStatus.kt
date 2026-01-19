package com.deniscerri.ytdl.database.models

import kotlinx.serialization.Serializable

@Serializable
enum class DownloadStatus {
    Active,
    Cancelled,
    Error,
    Paused,
    PausedRedownload,
    Scheduled,
    Queued,
    QueuedRedownload,
    Saved,
    Processing,
    Duplicate
}
