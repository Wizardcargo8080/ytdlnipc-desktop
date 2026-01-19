package com.deniscerri.ytdl.platform

import com.deniscerri.ytdl.database.models.TerminalItem
import kotlinx.coroutines.*
import java.io.File

/**
 * PLATFORM-SPECIFIC: Replicates TerminalDownloadWorker logic for Desktop
 */
class TerminalExecutor(private val ytdlpPath: String) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun executeCustomCommand(
        terminalItem: TerminalItem,
        onOutput: (String) -> Unit,
        onComplete: (Int) -> Unit
    ): Job {
        return scope.launch {
            try {
                val processBuilder = ProcessBuilder()
                
                // Split command safely or use shell
                val fullCommand = if (terminalItem.command.startsWith("yt-dlp")) {
                    terminalItem.command.replace("yt-dlp", ytdlpPath)
                } else {
                    "$ytdlpPath ${terminalItem.command}"
                }
                
                val commandParts = fullCommand.split(" ") // Simple split, could be improved for quotes
                processBuilder.command(commandParts)
                processBuilder.redirectErrorStream(true)
                
                val process = processBuilder.start()
                process.inputStream.bufferedReader().use { reader ->
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        onOutput(line!!)
                    }
                }
                
                val exitCode = process.waitFor()
                onComplete(exitCode)
            } catch (e: Exception) {
                onOutput("Error: ${e.message}")
                onComplete(-1)
            }
        }
    }
}
