package com.deniscerri.ytdl.platform

import com.deniscerri.ytdl.core.YTDLPCommandBuilder
import java.io.File
import java.util.prefs.Preferences

/**
 * PLATFORM-SPECIFIC: Desktop preferences implementation
 * Replaces Android SharedPreferences with Java Preferences API
 */
class DesktopPreferences : YTDLPCommandBuilder.PreferencesProvider {
    
    private val prefs: Preferences = Preferences.userNodeForPackage(DesktopPreferences::class.java)
    
    override fun getBoolean(key: String, default: Boolean): Boolean {
        return prefs.getBoolean(key, default)
    }
    
    override fun getString(key: String, default: String): String {
        return prefs.get(key, default)
    }
    
    override fun getInt(key: String, default: Int): Int {
        return prefs.getInt(key, default)
    }
    
    override fun getStringSet(key: String, default: Set<String>): Set<String> {
        val value = prefs.get(key, null) ?: return default
        return value.split(",").toSet()
    }
    
    fun putBoolean(key: String, value: Boolean) {
        prefs.putBoolean(key, value)
    }
    
    fun putString(key: String, value: String) {
        prefs.put(key, value)
    }
    
    fun putInt(key: String, value: Int) {
        prefs.putInt(key, value)
    }
    
    fun putStringSet(key: String, value: Set<String>) {
        prefs.put(key, value.joinToString(","))
    }
    
    fun remove(key: String) {
        prefs.remove(key)
    }
    
    fun clear() {
        prefs.clear()
    }
}

/**
 * PLATFORM-SPECIFIC: Desktop file provider implementation
 * Replaces Android FileUtil with desktop file operations
 */
class DesktopFileProvider : YTDLPCommandBuilder.FileProvider {
    
    private val appDataDir: File by lazy {
        val os = System.getProperty("os.name").lowercase()
        when {
            os.contains("win") -> {
                File(System.getenv("APPDATA"), "YTDLnis")
            }
            os.contains("mac") -> {
                File(System.getProperty("user.home"), "Library/Application Support/YTDLnis")
            }
            else -> {
                File(System.getProperty("user.home"), ".config/ytdlnis")
            }
        }.also { it.mkdirs() }
    }
    
    override fun getCachePath(): String {
        return File(appDataDir, "cache").apply { mkdirs() }.absolutePath
    }
    
    override fun formatPath(path: String): String {
        return path.replace("\\", "/")
    }
    
    override fun canWrite(path: String): Boolean {
        return File(path).canWrite()
    }
    
    override fun getCookieFilePath(): String? {
        val cookieFile = File(appDataDir, "cookies.txt")
        return if (cookieFile.exists()) cookieFile.absolutePath else null
    }
    
    override fun getDownloadArchivePath(): String {
        return File(appDataDir, "download_archive.txt").apply { 
            if (!exists()) createNewFile() 
        }.absolutePath
    }
    
    fun getDefaultAudioPath(): String {
        val os = System.getProperty("os.name").lowercase()
        return when {
            os.contains("win") -> {
                File(System.getProperty("user.home"), "Music/YTDLnis").apply { mkdirs() }.absolutePath
            }
            os.contains("mac") -> {
                File(System.getProperty("user.home"), "Music/YTDLnis").apply { mkdirs() }.absolutePath
            }
            else -> {
                File(System.getProperty("user.home"), "Music/YTDLnis").apply { mkdirs() }.absolutePath
            }
        }
    }
    
    fun getDefaultVideoPath(): String {
        val os = System.getProperty("os.name").lowercase()
        return when {
            os.contains("win") -> {
                File(System.getProperty("user.home"), "Videos/YTDLnis").apply { mkdirs() }.absolutePath
            }
            os.contains("mac") -> {
                File(System.getProperty("user.home"), "Movies/YTDLnis").apply { mkdirs() }.absolutePath
            }
            else -> {
                File(System.getProperty("user.home"), "Videos/YTDLnis").apply { mkdirs() }.absolutePath
            }
        }
    }
    
    fun getDefaultCommandPath(): String {
        val os = System.getProperty("os.name").lowercase()
        return when {
            os.contains("win") -> {
                File(System.getProperty("user.home"), "Downloads/YTDLnis").apply { mkdirs() }.absolutePath
            }
            else -> {
                File(System.getProperty("user.home"), "Downloads/YTDLnis").apply { mkdirs() }.absolutePath
            }
        }
    }
    
    fun getAppDataDir(): String {
        return appDataDir.absolutePath
    }
    
    fun getBinariesDir(): String {
        return File(appDataDir, "bin").apply { mkdirs() }.absolutePath
    }
}

/**
 * PLATFORM-SPECIFIC: Desktop resource provider implementation
 * Loads strings from bundled resources
 */
class DesktopResourceProvider : YTDLPCommandBuilder.ResourceProvider {
    
    private val strings: Map<String, String> by lazy {
        // Load from bundled strings.xml or properties file
        mapOf(
            "best_quality" to "Best Quality",
            "worst_quality" to "Worst Quality",
            "defaultValue" to "Default"
        )
    }
    
    private val arrays: Map<String, Array<String>> by lazy {
        mapOf(
            "audio_containers" to arrayOf("Default", "mp3", "m4a", "aac", "alac", "flac", "opus", "wav"),
            "audio_containers_values" to arrayOf("", "mp3", "m4a", "aac", "alac", "flac", "opus", "wav"),
            "video_containers" to arrayOf("Default", "mp4", "webm", "mkv", "mov", "avi", "flv", "gif"),
            "video_containers_values" to arrayOf("", "mp4", "webm", "mkv", "mov", "avi", "flv", "gif"),
            "video_formats_values" to arrayOf("best", "2160p_ytdlnisgeneric", "1440p_ytdlnisgeneric", 
                "1080p_ytdlnisgeneric", "720p_ytdlnisgeneric", "480p_ytdlnisgeneric", 
                "360p_ytdlnisgeneric", "240p_ytdlnisgeneric", "worst"),
            "audio_formats_values" to arrayOf("ba", "192kbps_ytdlnisgeneric", "160kbps_ytdlnisgeneric", 
                "128kbps_ytdlnisgeneric", "96kbps_ytdlnisgeneric", "64kbps_ytdlnisgeneric", "wa"),
            "sponsorblock_settings_values" to arrayOf("music_offtopic", "sponsor", "intro", "outro", 
                "selfpromo", "preview", "filler", "interaction", "hook"),
            "video_codec_values" to arrayOf("", "av1", "vp9", "h264"),
            "video_codec_values_ytdlp" to arrayOf("", "av01", "vp9", "avc"),
            "audio_codec_values" to arrayOf("", "opus", "vorbis", "aac"),
            "audio_codec_values_ytdlp" to arrayOf("", "opus", "vorbis", "aac")
        )
    }
    
    override fun getString(key: String): String {
        return strings[key] ?: key
    }
    
    override fun getStringArray(key: String): Array<String> {
        return arrays[key] ?: emptyArray()
    }
}
