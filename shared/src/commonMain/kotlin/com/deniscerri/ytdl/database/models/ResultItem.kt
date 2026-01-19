package com.deniscerri.ytdl.database.models

import kotlinx.serialization.Serializable

@Serializable
data class ResultItem(
    var id: Long,
    var url: String,
    var title: String,
    var author: String,
    val duration: String,
    val thumb: String,
    val website: String,
    var playlistTitle: String,
    var formats: MutableList<Format>,
    var urls: String = "",
    var chapters: MutableList<ChapterItem>?,
    var playlistURL: String? = "",
    var playlistIndex: Int? = null,
    var creationTime: Long = System.currentTimeMillis() / 1000,
    var availableSubtitles: List<String> = listOf()
)
