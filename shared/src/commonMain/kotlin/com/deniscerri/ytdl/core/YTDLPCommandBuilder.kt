package com.deniscerri.ytdl.core

import com.deniscerri.ytdl.database.models.DownloadItem
import com.deniscerri.ytdl.database.models.DownloadType
import com.deniscerri.ytdl.database.models.Format
import com.deniscerri.ytdl.util.isYoutubeURL
import com.deniscerri.ytdl.util.getIDFromYoutubeURL
import java.io.File
import java.util.StringJoiner
import java.util.UUID

/**
 * CORE: YTDLPCommandBuilder extracted from Android YTDLPUtil.kt
 * Contains platform-agnostic yt-dlp argument construction logic.
 * Behavioral identity with Android version is required.
 */
class YTDLPCommandBuilder(
    private val preferences: PreferencesProvider,
    private val fileProvider: FileProvider,
    private val resourceProvider: ResourceProvider
) {
    
    /**
     * Interface for platform-specific preferences access
     */
    interface PreferencesProvider {
        fun getBoolean(key: String, default: Boolean): Boolean
        fun getString(key: String, default: String): String
        fun getInt(key: String, default: Int): Int
        fun getStringSet(key: String, default: Set<String>): Set<String>
    }
    
    /**
     * Interface for platform-specific file operations
     */
    interface FileProvider {
        fun getCachePath(): String
        fun formatPath(path: String): String
        fun canWrite(path: String): Boolean
        fun getCookieFilePath(): String?
        fun getDownloadArchivePath(): String
    }
    
    /**
     * Interface for platform-specific resource strings
     */
    interface ResourceProvider {
        fun getString(key: String): String
        fun getStringArray(key: String): Array<String>
    }
    
    /**
     * Result of building a yt-dlp command
     */
    data class YTDLPRequest(
        val url: String?,
        val arguments: List<String>,
        val configFilePath: String?
    )
    
    /**
     * Build yt-dlp request arguments - EXACT port of Android buildYoutubeDLRequest()
     */
    fun buildYoutubeDLRequest(downloadItem: DownloadItem): YTDLPRequest {
        var useItemURL = preferences.getBoolean("use_itemurl_instead_playlisturl", false)
        
        // for /releases youtube channel playlists that have playlists inside of them
        if (downloadItem.url.isYoutubeURL() && downloadItem.url.getIDFromYoutubeURL() == null) {
            useItemURL = true
        }
        
        var isPlaylistItem = false
        val request = StringJoiner(" ")
        val arguments = mutableListOf<String>()
        
        val baseUrl: String? = when {
            downloadItem.url.endsWith(".txt") -> {
                request.addOption("-a", downloadItem.url)
                null
            }
            downloadItem.playlistURL.isNullOrBlank() || downloadItem.playlistTitle.isBlank() || useItemURL -> {
                if (downloadItem.url.isBlank()) null else downloadItem.url
            }
            else -> {
                isPlaylistItem = true
                if (downloadItem.playlistIndex == null || downloadItem.url.isYoutubeURL() && downloadItem.url.getIDFromYoutubeURL() != null) {
                    request.addOption("--match-filter", "id~='${downloadItem.url.getIDFromYoutubeURL()}'")
                } else {
                    request.addOption("-I", "${downloadItem.playlistIndex!!}:${downloadItem.playlistIndex}")
                }
                downloadItem.playlistURL
            }
        }
        
        request.addOption("--newline")
        
        val metadataCommands = StringJoiner(" ")
        
        if (downloadItem.playlistIndex != null && useItemURL) {
            metadataCommands.addOption("--parse-metadata", " ${downloadItem.playlistIndex}: %(playlist_index)s")
        }
        
        if (downloadItem.rowNumber > 0) {
            request.addOption("--autonumber-start", downloadItem.rowNumber.toString())
        }
        
        val type = downloadItem.type
        val canWrite = fileProvider.canWrite(fileProvider.formatPath(downloadItem.downloadPath))
        val writtenPath = type == DownloadType.command && downloadItem.format.format_note.contains("-P ")
        
        val downDir: String = if (writtenPath || (!preferences.getBoolean("cache_downloads", true) && canWrite)) {
            request.addOption("--no-quiet")
            request.addOption("--no-simulate")
            request.addOption("--print", "after_move:'%(filepath,_filename)s'")
            fileProvider.formatPath(downloadItem.downloadPath)
        } else {
            val cacheDir = fileProvider.getCachePath()
            "$cacheDir/${downloadItem.id}"
        }
        
        // Aria2 downloader
        val aria2 = preferences.getBoolean("aria2", false)
        if (aria2) {
            request.addOption("--downloader", "libaria2c.so")
            request.addOption("--no-check-certificates")
        }
        
        // Concurrent fragments
        val concurrentFragments = preferences.getInt("concurrent_fragments", 1)
        if (concurrentFragments > 1) {
            request.addOption("-N", concurrentFragments)
        }
        
        // Retries
        val retries = preferences.getString("retries", "")
        val fragmentRetries = preferences.getString("fragment_retries", "")
        if (retries.isNotEmpty()) request.addOption("--retries", retries)
        if (fragmentRetries.isNotEmpty()) request.addOption("--fragment-retries", fragmentRetries)
        
        // Rate limit
        val limitRate = preferences.getString("limit_rate", "")
        if (limitRate.isNotBlank()) request.addOption("-r", limitRate)
        
        // Buffer size
        val bufferSize = preferences.getString("buffer_size", "")
        if (bufferSize.isNotBlank()) {
            request.addOption("--buffer-size", bufferSize)
            request.addOption("--no-resize-buffer")
        }
        
        // Socket timeout
        val socketTimeout = preferences.getString("socket_timeout", "")
        if (socketTimeout.isNotBlank()) {
            request.addOption("--socket-timeout", socketTimeout)
        }
        
        // Restrict filenames
        if (preferences.getBoolean("restrict_filenames", true)) {
            request.addOption("--restrict-filenames")
        }
        
        // Force IPv4
        if (preferences.getBoolean("force_ipv4", false)) {
            request.addOption("-4")
        }
        
        // Cookies
        if (preferences.getBoolean("use_cookies", false)) {
            val cookieFile = fileProvider.getCookieFilePath()
            if (cookieFile != null) {
                request.addOption("--cookies", cookieFile)
            }
        }
        
        // No check certificates
        if (preferences.getBoolean("no_check_certificates", false) && !request.toString().contains("--no-check-certificates")) {
            request.addOption("--no-check-certificates")
        }
        
        // Proxy
        val proxy = preferences.getString("proxy", "")
        if (proxy.isNotBlank()) {
            request.addOption("--proxy", proxy)
        }
        
        // Keep cache/fragments
        if (preferences.getBoolean("keep_cache", false)) {
            request.addOption("--keep-fragments")
        }
        
        val embedMetadata = preferences.getBoolean("embed_metadata", true)
        val thumbnailFormat = preferences.getString("thumbnail_format", "jpg")
        var filenameTemplate = downloadItem.customFileNameTemplate
        
        if (downloadItem.type != DownloadType.command) {
            // No part files
            if (preferences.getBoolean("no_part", false)) {
                request.addOption("--no-part")
            }
            
            request.addOption("--trim-filenames", (254 - downDir.length).toString())
            
            // Save thumbnail
            if (downloadItem.SaveThumb) {
                request.addOption("--write-thumbnail")
                request.addOption("--convert-thumbnails", thumbnailFormat)
            }
            
            // mtime
            if (!preferences.getBoolean("mtime", false)) {
                request.addOption("--no-mtime")
            } else {
                request.addOption("--mtime")
            }
            
            // SponsorBlock
            val sponsorBlockFilters = when (downloadItem.type) {
                DownloadType.audio -> downloadItem.audioPreferences.sponsorBlockFilters
                else -> downloadItem.videoPreferences.sponsorBlockFilters
            }
            
            if (preferences.getBoolean("use_sponsorblock", true)) {
                if (sponsorBlockFilters.isNotEmpty()) {
                    val filters = sponsorBlockFilters.filter { it.isNotBlank() }.joinToString(",")
                    if (filters.isNotBlank()) {
                        request.addOption("--sponsorblock-remove", filters)
                        if (preferences.getBoolean("force_keyframes", false)) {
                            request.addOption("--force-keyframes-at-cuts")
                        }
                    }
                }
            }
            
            // Title metadata
            if (downloadItem.title.isNotBlank()) {
                metadataCommands.addOption("--replace-in-metadata", "title", """^.*$""", downloadItem.title.replace(""""""", """\\""""))
                metadataCommands.addOption("--parse-metadata", "%(title)s:%(meta_title)s")
            }
            
            // Author metadata
            if (downloadItem.author.isNotBlank()) {
                metadataCommands.addOption("--replace-in-metadata", "uploader", """^.*$""", downloadItem.author.replace(""""""", """\\""""))
                metadataCommands.addOption("--parse-metadata", "%(uploader)s:%(artist)s")
            }
            
            // Download sections (cuts)
            if (downloadItem.downloadSections.isNotBlank()) {
                downloadItem.downloadSections.split(";").forEach { section ->
                    if (section.isBlank()) return@forEach
                    request.addOption("--download-sections", "*${section.split(" ")[0]}")
                    
                    if (preferences.getBoolean("force_keyframes", false) && !request.toString().contains("--force-keyframes-at-cuts")) {
                        request.addOption("--force-keyframes-at-cuts")
                    }
                }
                
                if (downloadItem.downloadSections.split(";").size > 1) {
                    filenameTemplate = "%(autonumber)d. $filenameTemplate [%(section_start>%H∶%M∶%S)s]"
                }
            }
            
            // Write description
            if (preferences.getBoolean("write_description", false)) {
                request.addOption("--write-description")
            }
        }
        
        // Download archive
        if (preferences.getString("prevent_duplicate_downloads", "") == "download_archive") {
            request.addOption("--download-archive", fileProvider.getDownloadArchivePath())
        }
        
        // Type-specific processing
        when (type) {
            DownloadType.audio -> buildAudioRequest(request, metadataCommands, downloadItem, downDir, filenameTemplate, embedMetadata, thumbnailFormat, isPlaylistItem)
            DownloadType.video -> buildVideoRequest(request, metadataCommands, downloadItem, downDir, filenameTemplate, embedMetadata, thumbnailFormat)
            DownloadType.command -> {
                if (!writtenPath) {
                    request.addOption("-P", downDir)
                }
                request.addOption(downloadItem.format.format_note)
            }
            else -> {}
        }
        
        request.merge(metadataCommands)
        
        // Extra commands
        if (downloadItem.extraCommands.isNotBlank() && downloadItem.type != DownloadType.command) {
            val cacheDirArg = """--cache-dir\s+(?:"([^"]+)"|(\S+))""".toRegex().find(downloadItem.extraCommands)
            if (cacheDirArg != null) {
                arguments.add("--cache-dir")
                arguments.add(cacheDirArg.groupValues.last().replace("\"", ""))
            }
            request.addOption(downloadItem.extraCommands)
        }
        
        // SponsorBlock API URL
        if (request.toString().contains("sponsorblock")) {
            val sponsorBlockURL = preferences.getString("sponsorblock_url", "")
            if (sponsorBlockURL.isNotBlank()) {
                request.addOption("--sponsorblock-api", sponsorBlockURL)
            }
        }
        
        // Write config to file
        val cachePath = fileProvider.getCachePath()
        val configFilePath = "$cachePath/${System.currentTimeMillis()}${UUID.randomUUID()}.txt"
        
        return YTDLPRequest(
            url = baseUrl,
            arguments = arguments + listOf("--config-locations", configFilePath),
            configFilePath = configFilePath
        ).also {
            // The config content should be written by the caller
            // configContent = request.toString()
        }
    }
    
    private fun buildAudioRequest(
        request: StringJoiner,
        metadataCommands: StringJoiner,
        downloadItem: DownloadItem,
        downDir: String,
        filenameTemplate: String,
        embedMetadata: Boolean,
        thumbnailFormat: String,
        isPlaylistItem: Boolean
    ) {
        val supportedContainers = resourceProvider.getStringArray("audio_containers")
        val preferredLanguage = preferences.getString("audio_language", "")
        
        var audioQualityId = downloadItem.format.format_id
        var abrSort = ""
        
        val genericFormats = listOf("0", resourceProvider.getString("best_quality"), "ba", "best", "", 
            resourceProvider.getString("worst_quality"), "wa", "worst")
        
        if (audioQualityId.isBlank() || genericFormats.contains(audioQualityId)) {
            audioQualityId = "ba/b"
        } else if (audioQualityId.contains("kbps_ytdlnisgeneric")) {
            abrSort = audioQualityId.split("kbps")[0]
            audioQualityId = ""
        } else {
            audioQualityId += "/ba/b"
        }
        
        if ((audioQualityId.isBlank() || audioQualityId == "ba/b") && preferredLanguage.isNotBlank()) {
            audioQualityId = "ba[language^=$preferredLanguage]/ba/b"
        }
        
        if (audioQualityId.isNotBlank()) {
            if (audioQualityId.matches(".*-[0-9]+.*".toRegex())) {
                audioQualityId = if (!downloadItem.format.lang.isNullOrBlank() && downloadItem.format.lang != "None") {
                    "ba[format_id~='^(${audioQualityId.split("-")[0]})'][language^=${downloadItem.format.lang}]/ba/b"
                } else {
                    "$audioQualityId/${audioQualityId.split("-")[0]}"
                }
            }
            request.addOption("-f", audioQualityId)
        }
        
        request.addOption("-x")
        
        val ext = downloadItem.container
        val formatSorting = mutableListOf<String>()
        
        if (preferences.getBoolean("use_format_sorting", false)) {
            formatSorting.add("hasaud")
        }
        
        // Container/format handling
        if (ext.isNotBlank() && !ext.matches("(webm)|(Default)".toRegex()) && supportedContainers.contains(ext)) {
            request.addOption("--audio-format", ext)
            formatSorting.add("aext:$ext")
        }
        
        if (downloadItem.format.format_id == resourceProvider.getString("worst_quality") || 
            downloadItem.format.format_id == "wa" || downloadItem.format.format_id == "worst") {
            formatSorting.remove("size")
            formatSorting.remove("+size")
            formatSorting.addAll(0, listOf("+br", "+res", "+fps"))
        }
        
        if (abrSort.isNotBlank()) {
            formatSorting.add(0, "abr:$abrSort")
        }
        
        if (downloadItem.downloadSections.isNotBlank()) {
            formatSorting.add(0, "proto:https")
        }
        
        if (formatSorting.isNotEmpty()) {
            request.addOption("-S", formatSorting.joinToString(","))
        }
        
        request.addOption("-P", downDir)
        
        // Split by chapters
        if (downloadItem.audioPreferences.splitByChapters && downloadItem.downloadSections.isBlank()) {
            request.addOption("--split-chapters")
            request.addOption("-o", "chapter:%(section_title)s.%(ext)s")
        } else {
            if (embedMetadata) {
                metadataCommands.addOption("--embed-metadata")
            }
            
            // Embed thumbnail
            val cropThumb = downloadItem.audioPreferences.cropThumb ?: preferences.getBoolean("crop_thumbnail", true)
            if (downloadItem.audioPreferences.embedThumb) {
                metadataCommands.addOption("--embed-thumbnail")
                if (!request.toString().contains("--convert-thumbnails")) {
                    metadataCommands.addOption("--convert-thumbnails", thumbnailFormat)
                }
                
                val thumbnailConfig = StringBuilder("")
                val cropConfig = """-vf crop="'if(gt(ih,iw),iw,ih)':'if(gt(iw,ih),ih,iw)'""""
                if (thumbnailFormat == "jpg") {
                    thumbnailConfig.append("""--ppa "ThumbnailsConvertor:-qmin 1 -q:v 1"""")
                }
                if (cropThumb) {
                    if (thumbnailFormat == "jpg") {
                        thumbnailConfig.deleteCharAt(thumbnailConfig.length - 1)
                        thumbnailConfig.append(""" $cropConfig""")
                    } else {
                        thumbnailConfig.append("""--ppa "ThumbnailsConvertor:$cropConfig"""")
                    }
                }
                
                if (thumbnailConfig.isNotBlank()) {
                    request.addOption(thumbnailConfig.toString())
                }
            }
            
            if (filenameTemplate.isNotBlank()) {
                request.addOption("-o", "${filenameTemplate.removeSuffix(".%(ext)s")}.%(ext)s")
            }
        }
        
        // Audio bitrate
        if (downloadItem.audioPreferences.bitrate.isNotBlank()) {
            request.addOption("--audio-quality", downloadItem.audioPreferences.bitrate)
        }
    }
    
    private fun buildVideoRequest(
        request: StringJoiner,
        metadataCommands: StringJoiner,
        downloadItem: DownloadItem,
        downDir: String,
        filenameTemplate: String,
        embedMetadata: Boolean,
        thumbnailFormat: String
    ) {
        val supportedContainers = resourceProvider.getStringArray("video_containers")
        
        // Chapters
        if (downloadItem.videoPreferences.addChapters) {
            if (preferences.getBoolean("use_sponsorblock", true)) {
                request.addOption("--sponsorblock-mark", "all")
            }
            request.addOption("--embed-chapters")
        }
        
        // Container/format
        val outputContainer = downloadItem.container
        if (outputContainer.isNotEmpty() && outputContainer != "Default" && supportedContainers.contains(outputContainer)) {
            val cantRecode = listOf("avi")
            if (downloadItem.videoPreferences.recodeVideo && !cantRecode.contains(outputContainer)) {
                request.addOption("--recode-video", outputContainer.lowercase())
            } else {
                if (downloadItem.videoPreferences.compatibilityMode) {
                    request.addOption("--recode-video", "mp4")
                    request.addOption("--merge-output-format", "mp4/mkv")
                } else {
                    request.addOption("--merge-output-format", outputContainer.lowercase())
                }
            }
            
            if (!listOf("webm", "avi", "flv", "gif").contains(outputContainer.lowercase())) {
                if (downloadItem.videoPreferences.embedThumbnail) {
                    metadataCommands.addOption("--embed-thumbnail")
                    if (!request.toString().contains("--convert-thumbnails")) {
                        request.addOption("--convert-thumbnails", thumbnailFormat)
                    }
                }
            }
        }
        
        // Format selection
        var videoF = downloadItem.format.format_id
        var audioF = downloadItem.videoPreferences.audioFormatIDs.joinToString("+").ifBlank { "ba" }
        
        if (downloadItem.videoPreferences.removeAudio) audioF = ""
        
        val f = StringBuilder()
        
        val defaultFormats = resourceProvider.getStringArray("video_formats_values")
        val usingGenericFormat = defaultFormats.contains(videoF) || downloadItem.allFormats.isEmpty()
        
        if (!usingGenericFormat) {
            if (audioF.isBlank()) {
                f.append("$videoF/bv/b")
            } else {
                f.append("$videoF+$audioF/$videoF+ba/$videoF/b")
            }
        } else {
            if (videoF == resourceProvider.getString("best_quality") || videoF == "best" ||
                videoF == resourceProvider.getString("worst_quality") || videoF == "worst") {
                videoF = "bv"
            }
            
            if (audioF.isBlank()) {
                f.append("$videoF/bv/b")
            } else {
                f.append("$videoF+$audioF/$videoF+ba/$videoF/b")
            }
        }
        
        val formatSorting = mutableListOf<String>()
        
        if (downloadItem.format.format_id == resourceProvider.getString("worst_quality") || 
            downloadItem.format.format_id == "worst") {
            formatSorting.addAll(0, listOf("+br", "+res", "+fps"))
        }
        
        if (downloadItem.downloadSections.isNotBlank()) {
            formatSorting.add(0, "proto:https")
        }
        
        if (formatSorting.isNotEmpty()) {
            request.addOption("-S", formatSorting.joinToString(","))
        }
        
        request.addOption("-f", f.toString().replace("/$".toRegex(), ""))
        
        // Subtitles
        if (downloadItem.videoPreferences.writeSubs) {
            request.addOption("--write-subs")
        }
        
        if (downloadItem.videoPreferences.writeAutoSubs) {
            request.addOption("--write-auto-subs")
        }
        
        if (downloadItem.videoPreferences.embedSubs) {
            if (preferences.getBoolean("no_keep_subs", false) && 
                (downloadItem.videoPreferences.writeSubs || downloadItem.videoPreferences.writeAutoSubs)) {
                request.addOption("--compat-options", "no-keep-subs")
            }
            request.addOption("--embed-subs")
        }
        
        if (downloadItem.videoPreferences.embedSubs || downloadItem.videoPreferences.writeSubs || 
            downloadItem.videoPreferences.writeAutoSubs) {
            val subFormat = preferences.getString("sub_format", "")
            if (subFormat.isNotBlank()) {
                request.addOption("--sub-format", "$subFormat/best")
                request.addOption("--convert-subtitles", subFormat)
            }
            request.addOption("--sub-langs", downloadItem.videoPreferences.subsLanguages.ifEmpty { "en.*,.*-orig" })
        }
        
        // Remove audio
        if (downloadItem.videoPreferences.removeAudio && outputContainer != "gif") {
            request.addOption("--use-postprocessor", "FFmpegCopyStream")
            request.addOption("--ppa", "CopyStream:-c copy -an")
        }
        
        request.addOption("-P", downDir)
        
        // Split by chapters
        if (downloadItem.videoPreferences.splitByChapters && downloadItem.downloadSections.isBlank()) {
            request.addOption("--split-chapters")
            request.addOption("-o", "chapter:%(section_number)d - %(section_title)s.%(ext)s")
        } else {
            if (filenameTemplate.isNotBlank()) {
                request.addOption("-o", "${filenameTemplate.removeSuffix(".%(ext)s")}.%(ext)s")
            }
        }
        
        // Live stream options
        if (downloadItem.videoPreferences.liveFromStart) {
            request.addOption("--live-from-start")
        }
        
        if (downloadItem.videoPreferences.waitForVideoMinutes > 0) {
            request.addOption("--wait-for-video", (downloadItem.videoPreferences.waitForVideoMinutes * 60).toString())
        }
    }
    
    // Extension functions for StringJoiner
    private fun StringJoiner.addOption(vararg elements: Any) {
        this.add(elements.first().toString())
        if (elements.size > 1) {
            for (el in elements.drop(1)) {
                val arg = el.toString().replace("\"", "\\\"")
                this.add("\"$arg\"")
            }
        }
    }
    
    private fun StringJoiner.merge(other: StringJoiner) {
        this.merge(other)
    }
}
