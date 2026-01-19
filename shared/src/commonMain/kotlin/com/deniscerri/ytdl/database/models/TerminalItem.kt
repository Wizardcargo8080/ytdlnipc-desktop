package com.deniscerri.ytdl.database.models

import kotlinx.serialization.Serializable

@Serializable
data class TerminalItem(
    var id: Long = 0,
    var command: String,
    var log: String? = "",
)
