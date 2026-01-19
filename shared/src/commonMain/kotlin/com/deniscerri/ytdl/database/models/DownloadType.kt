package com.deniscerri.ytdl.database.models

import kotlinx.serialization.Serializable

@Serializable
enum class DownloadType {
    auto, audio, video, command
}
