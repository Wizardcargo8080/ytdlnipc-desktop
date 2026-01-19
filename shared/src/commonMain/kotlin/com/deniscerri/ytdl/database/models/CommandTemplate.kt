package com.deniscerri.ytdl.database.models

import kotlinx.serialization.Serializable

@Serializable
data class CommandTemplate(
    var id: Long,
    var title: String,
    var content: String,
    var useAsExtraCommand: Boolean = false,
    var useAsExtraCommandAudio: Boolean = true,
    var useAsExtraCommandVideo: Boolean = true,
    var useAsExtraCommandDataFetching: Boolean = false,
    var preferredCommandTemplate: Boolean = false,
    var urlRegex: MutableList<String> = mutableListOf()
)
