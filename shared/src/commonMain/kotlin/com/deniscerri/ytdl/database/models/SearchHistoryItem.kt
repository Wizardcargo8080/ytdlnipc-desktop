package com.deniscerri.ytdl.database.models

import kotlinx.serialization.Serializable

@Serializable
data class SearchHistoryItem(
    var id: Long,
    val query: String,
    val time: Long = 0
)
