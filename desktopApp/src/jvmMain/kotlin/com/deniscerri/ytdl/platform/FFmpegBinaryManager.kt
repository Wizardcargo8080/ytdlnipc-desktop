package com.deniscerri.ytdl.platform

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

/**
 * PLATFORM-SPECIFIC: FFmpeg binary management for Desktop
 * Handles finding, downloading, and executing ffmpeg/ffprobe binaries
 */
object FFmpegBinaryManager {
    
    private val osName = System.getProperty("os.name").lowercase()
    private val isWindows = osName.contains("win")
    private val isMac = osName.contains("mac")
    private val isLinux = osName.contains("nix") || osName.contains("nux")
    
    private val binaryName = if (isWindows) "ffmpeg.exe" else "ffmpeg"
    private val ffprobeName = if (isWindows) "ffprobe.exe" else "ffprobe"
    
    private val appDataDir = getAppDataDirectory()
    private val binDir = File(appDataDir, "bin")
    
    /**
     * Get the platform-specific app data directory
     */
    private fun getAppDataDirectory(): File {
        val path = when {
            isWindows -> System.getenv("APPDATA")?.let { "$it/YTDLnis" } 
                ?: "${System.getProperty("user.home")}/AppData/Roaming/YTDLnis"
            isMac -> "${System.getProperty("user.home")}/Library/Application Support/YTDLnis"
            else -> System.getenv("XDG_DATA_HOME")?.let { "$it/ytdlnis" } 
                ?: "${System.getProperty("user.home")}/.local/share/ytdlnis"
        }
        return File(path).also { it.mkdirs() }
    }
    
    /**
     * Get the path to the ffmpeg binary
     * First checks bundled binary, then system PATH
     */
    fun getFFmpegPath(): String {
        // Check bundled binary first
        val bundledPath = File(binDir, binaryName)
        if (bundledPath.exists() && bundledPath.canExecute()) {
            return bundledPath.absolutePath
        }
        
        // Check system PATH
        val systemPath = findInSystemPath(binaryName)
        if (systemPath != null) {
            return systemPath
        }
        
        // Default to just the binary name (will fail if not found)
        return binaryName
    }
    
    /**
     * Get the path to the ffprobe binary
     */
    fun getFFprobePath(): String {
        // Check bundled binary first
        val bundledPath = File(binDir, ffprobeName)
        if (bundledPath.exists() && bundledPath.canExecute()) {
            return bundledPath.absolutePath
        }
        
        // Check system PATH
        val systemPath = findInSystemPath(ffprobeName)
        if (systemPath != null) {
            return systemPath
        }
        
        // Default to just the binary name
        return ffprobeName
    }
    
    /**
     * Check if ffmpeg is available
     */
    fun isFFmpegAvailable(): Boolean {
        return try {
            val process = ProcessBuilder(getFFmpegPath(), "-version")
                .redirectErrorStream(true)
                .start()
            val exitCode = process.waitFor()
            exitCode == 0
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * Check if ffprobe is available
     */
    fun isFFprobeAvailable(): Boolean {
        return try {
            val process = ProcessBuilder(getFFprobePath(), "-version")
                .redirectErrorStream(true)
                .start()
            val exitCode = process.waitFor()
            exitCode == 0
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * Get ffmpeg version string
     */
    fun getFFmpegVersion(): String? {
        return try {
            val process = ProcessBuilder(getFFmpegPath(), "-version")
                .redirectErrorStream(true)
                .start()
            val output = process.inputStream.bufferedReader().readLine()
            process.waitFor()
            output?.substringAfter("ffmpeg version ")?.substringBefore(" ")
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Find binary in system PATH
     */
    private fun findInSystemPath(binaryName: String): String? {
        val pathEnv = System.getenv("PATH") ?: return null
        val pathSeparator = if (isWindows) ";" else ":"
        
        for (pathDir in pathEnv.split(pathSeparator)) {
            val file = File(pathDir, binaryName)
            if (file.exists() && file.canExecute()) {
                return file.absolutePath
            }
        }
        return null
    }
    
    /**
     * Install ffmpeg binary from a given source path
     * Used when user provides their own binary
     */
    fun installFFmpegFromPath(sourcePath: String): Boolean {
        return try {
            binDir.mkdirs()
            val source = Paths.get(sourcePath)
            val target = Paths.get(binDir.absolutePath, binaryName)
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING)
            
            // Make executable on Unix
            if (!isWindows) {
                target.toFile().setExecutable(true)
            }
            true
        } catch (e: Exception) {
            println("Failed to install ffmpeg: ${e.message}")
            false
        }
    }
    
    /**
     * Install ffprobe binary from a given source path
     */
    fun installFFprobeFromPath(sourcePath: String): Boolean {
        return try {
            binDir.mkdirs()
            val source = Paths.get(sourcePath)
            val target = Paths.get(binDir.absolutePath, ffprobeName)
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING)
            
            // Make executable on Unix
            if (!isWindows) {
                target.toFile().setExecutable(true)
            }
            true
        } catch (e: Exception) {
            println("Failed to install ffprobe: ${e.message}")
            false
        }
    }
    
    /**
     * Get status information about ffmpeg installation
     */
    fun getStatus(): FFmpegStatus {
        return FFmpegStatus(
            ffmpegAvailable = isFFmpegAvailable(),
            ffprobeAvailable = isFFprobeAvailable(),
            ffmpegPath = getFFmpegPath(),
            ffprobePath = getFFprobePath(),
            ffmpegVersion = getFFmpegVersion()
        )
    }
}

/**
 * Data class to hold ffmpeg installation status
 */
data class FFmpegStatus(
    val ffmpegAvailable: Boolean,
    val ffprobeAvailable: Boolean,
    val ffmpegPath: String,
    val ffprobePath: String,
    val ffmpegVersion: String?
)
