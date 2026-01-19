package com.deniscerri.ytdl.platform

import com.deniscerri.ytdl.database.models.ChapterItem
import com.deniscerri.ytdl.database.models.Format
import com.deniscerri.ytdl.database.models.ResultItem
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * PLATFORM-SPECIFIC: Executes yt-dlp commands via ProcessBuilder
 */
object YTDLPExecutor {
    
    data class DownloadResult(
        val isSuccess: Boolean,
        val outputFile: String? = null,
        val error: String? = null
    )

    /**
     * Execute a download command and track progress
     */
    suspend fun executeDownload(
        command: List<String>,
        outputDir: File,
        onProgress: (Float) -> Unit
    ): DownloadResult = withContext(Dispatchers.IO) {
        try {
            outputDir.mkdirs()
            
            val process = ProcessBuilder(command)
                .directory(outputDir)
                .redirectErrorStream(true)
                .start()

            val reader = process.inputStream.bufferedReader()
            var line: String?
            
            while (reader.readLine().also { line = it } != null) {
                line?.let { parseProgressLine(it, onProgress) }
            }

            val exitCode = process.waitFor()
            
            if (exitCode == 0) {
                DownloadResult(isSuccess = true)
            } else {
                DownloadResult(isSuccess = false, error = "Exit code: $exitCode")
            }
        } catch (e: Exception) {
            DownloadResult(isSuccess = false, error = e.message)
        }
    }

    private fun parseProgressLine(line: String, onProgress: (Float) -> Unit) {
        // Parse yt-dlp progress output: [download]  45.2% of 100.00MiB at 5.00MiB/s ETA 00:10
        val progressRegex = """\[download\]\s+(\d+\.?\d*)%""".toRegex()
        progressRegex.find(line)?.let { match ->
            val progress = match.groupValues[1].toFloatOrNull() ?: 0f
            onProgress(progress / 100f)
        }
    }

    /**
     * Fetch video info using yt-dlp -J
     */
    suspend fun fetchVideoInfo(url: String): Result<ResultItem> = withContext(Dispatchers.IO) {
        try {
            val command = listOf("yt-dlp", "-J", "--flat-playlist", url)
            
            val process = ProcessBuilder(command)
                .redirectErrorStream(false)
                .start()

            val output = process.inputStream.bufferedReader().readText()
            val errorOutput = process.errorStream.bufferedReader().readText()
            
            process.waitFor(60, TimeUnit.SECONDS)
            
            if (process.exitValue() != 0) {
                return@withContext Result.failure(Exception(errorOutput.ifBlank { "yt-dlp failed" }))
            }

            val json = JsonParser.parseString(output).asJsonObject
            val result = parseResultItem(json)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun parseResultItem(json: JsonObject): ResultItem {
        val formats = mutableListOf<Format>()
        
        json.getAsJsonArray("formats")?.forEach { element ->
            val formatJson = element.asJsonObject
            formats.add(parseFormat(formatJson))
        }

        val chapters = mutableListOf<ChapterItem>()
        json.getAsJsonArray("chapters")?.forEach { element ->
            val chapterJson = element.asJsonObject
            chapters.add(ChapterItem(
                start_time = chapterJson.get("start_time")?.asLong ?: 0L,
                end_time = chapterJson.get("end_time")?.asLong ?: 0L,
                title = chapterJson.get("title")?.asString ?: ""
            ))
        }

        return ResultItem(
            id = json.get("id")?.asLong ?: 0L,
            url = json.get("webpage_url")?.asString ?: json.get("url")?.asString ?: "",
            title = json.get("title")?.asString ?: "Unknown",
            author = json.get("uploader")?.asString ?: json.get("channel")?.asString ?: "",
            duration = formatDuration(json.get("duration")?.asInt ?: 0),
            thumb = json.get("thumbnail")?.asString ?: "",
            website = json.get("extractor")?.asString ?: "",
            playlistTitle = json.get("playlist_title")?.asString ?: "",
            formats = formats,
            urls = "",
            chapters = chapters,
            playlistURL = json.get("playlist_url")?.asString ?: "",
            playlistIndex = json.get("playlist_index")?.asInt ?: 0,
            availableSubtitles = listOf()
        )
    }

    private fun parseFormat(json: JsonObject): Format {
        return Format(
            format_id = json.get("format_id")?.asString ?: "",
            format_note = json.get("format_note")?.asString ?: "",
            container = json.get("ext")?.asString ?: "",
            vcodec = json.get("vcodec")?.asString ?: "none",
            acodec = json.get("acodec")?.asString ?: "none",
            filesize = json.get("filesize")?.asLong ?: json.get("filesize_approx")?.asLong ?: 0L,
            tbr = json.get("tbr")?.asString ?: ""
        )
    }

    private fun formatDuration(seconds: Int): String {
        if (seconds <= 0) return ""
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, secs)
        } else {
            String.format("%d:%02d", minutes, secs)
        }
    }
}
