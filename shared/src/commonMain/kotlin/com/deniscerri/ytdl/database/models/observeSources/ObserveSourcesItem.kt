package com.deniscerri.ytdl.database.models.observeSources

import com.deniscerri.ytdl.database.models.DownloadItem
import kotlinx.serialization.Serializable

@Serializable
enum class ObserveSourceStatus {
    ACTIVE, STOPPED
}

@Serializable
enum class EveryCategory {
    DAY, WEEK, MONTH, HOUR, MINUTE
}

@Serializable
data class ObserveSourcesWeeklyConfig(
    val weekDays: MutableList<String>,
    val everyWeekDay: Boolean
)

@Serializable
data class ObserveSourcesMonthlyConfig(
    val everyMonths: MutableList<Int>,
    val everyMonthsDay: Int
)

@Serializable
data class ObserveSourcesItem(
    var id: Long,
    var name: String,
    var url: String,
    var downloadItemTemplate: DownloadItem,

    var everyNr: Int,
    var everyCategory: EveryCategory,
    val everyTime: Long,
    var weeklyConfig: ObserveSourcesWeeklyConfig?,
    var monthlyConfig: ObserveSourcesMonthlyConfig?,

    var status: ObserveSourceStatus,
    var startsTime: Long,
    var endsDate: Long = 0,
    var endsAfterCount: Int = 0,
    var runCount: Int = 0,
    var getOnlyNewUploads: Boolean = false,
    var retryMissingDownloads: Boolean,
    var ignoredLinks: MutableList<String> = mutableListOf(),
    var alreadyProcessedLinks: MutableList<String> = mutableListOf(),
    var syncWithSource: Boolean = false
)
