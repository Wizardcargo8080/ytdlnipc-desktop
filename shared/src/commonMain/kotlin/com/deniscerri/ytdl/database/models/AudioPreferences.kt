package com.deniscerri.ytdl.database.models

import kotlinx.serialization.Serializable

@Serializable
data class AudioPreferences(
    var embedThumb: Boolean = true,
    var cropThumb: Boolean? = null,
    var splitByChapters: Boolean = false,
    var sponsorBlockFilters: ArrayList<String> = arrayListOf(),
    var bitrate: String = ""
)
