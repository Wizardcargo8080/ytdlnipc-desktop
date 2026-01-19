package com.deniscerri.ytdl.platform

import com.deniscerri.ytdl.database.models.DownloadItem
import com.deniscerri.ytdl.database.models.DownloadStatus
import com.deniscerri.ytdl.database.models.HistoryItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

/**
 * PLATFORM-SPECIFIC: Manages download queue and execution
 */
class DownloadManager {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val activeDownloads = mutableMapOf<Long, Job>()
    
    private val _queuedDownloads = MutableStateFlow<List<DownloadItem>>(emptyList())
    val queuedDownloads: StateFlow<List<DownloadItem>> = _queuedDownloads

    private val _activeDownloadsFlow = MutableStateFlow<List<DownloadItem>>(emptyList())
    val activeDownloadsFlow: StateFlow<List<DownloadItem>> = _activeDownloadsFlow

    fun queueDownload(item: DownloadItem) {
        _queuedDownloads.value = _queuedDownloads.value + item
    }

    fun startDownload(item: DownloadItem, onProgress: (Float) -> Unit, onComplete: (Boolean) -> Unit) {
        val job = scope.launch {
            try {
                _activeDownloadsFlow.value = _activeDownloadsFlow.value + item
                
                // Build command using YTDLPCommandBuilder (placeholder for now)
                val command = buildCommand(item)
                
                // Execute via YTDLPExecutor
                val result = YTDLPExecutor.executeDownload(
                    command = command,
                    outputDir = File(item.downloadPath),
                    onProgress = onProgress
                )
                
                onComplete(result.isSuccess)
            } catch (e: Exception) {
                e.printStackTrace()
                onComplete(false)
            } finally {
                _activeDownloadsFlow.value = _activeDownloadsFlow.value.filter { it.id != item.id }
                activeDownloads.remove(item.id)
            }
        }
        activeDownloads[item.id] = job
    }

    private fun buildCommand(item: DownloadItem): List<String> {
        // Placeholder - would use YTDLPCommandBuilder.buildYoutubeDLRequest()
        val command = mutableListOf("yt-dlp", "--newline", "-o", "${item.downloadPath}/%(title)s.%(ext)s")
        command.add(item.url)
        return command
    }

    fun cancelDownload(id: Long) {
        activeDownloads[id]?.cancel()
        activeDownloads.remove(id)
        _activeDownloadsFlow.value = _activeDownloadsFlow.value.filter { it.id != id }
    }

    fun cancelAllDownloads() {
        activeDownloads.forEach { (_, job) -> job.cancel() }
        activeDownloads.clear()
        _activeDownloadsFlow.value = emptyList()
    }
}
