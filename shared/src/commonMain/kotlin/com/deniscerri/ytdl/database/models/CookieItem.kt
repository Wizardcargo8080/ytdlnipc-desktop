package com.deniscerri.ytdl.database.models

import kotlinx.serialization.Serializable

@Serializable
data class CookieItem(
    var id: Long,
    var url: String,
    var content: String,
    var description: String = "",
    var enabled: Boolean = true
)
