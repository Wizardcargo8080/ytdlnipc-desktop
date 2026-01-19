package com.deniscerri.ytdl.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoNotDisturb
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Downloading Settings - matching Android downloading_preferences.xml
 */
@Composable
fun DownloadingSettingsScreen() {
    var showDownloadCard by remember { mutableStateOf(true) }
    var quickDownload by remember { mutableStateOf(false) }
    var cacheDownloads by remember { mutableStateOf(true) }
    var keepCache by remember { mutableStateOf(false) }
    var useScheduler by remember { mutableStateOf(false) }
    var incognito by remember { mutableStateOf(false) }
    var logDownloads by remember { mutableStateOf(false) }
    var useAria2 by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        PreferenceCategory("Download Card")
        
        SwitchPreference(
            title = "Show download card",
            summary = "Show bottom sheet when sharing URLs",
            icon = Icons.Default.Download,
            checked = showDownloadCard,
            onCheckedChange = { showDownloadCard = it }
        )
        
        SwitchPreference(
            title = "Quick download",
            summary = "Start download immediately without showing options",
            checked = quickDownload,
            onCheckedChange = { quickDownload = it }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        PreferenceCategory("Concurrency")
        
        PreferenceItem(
            title = "Concurrent downloads",
            summary = "1",
            icon = Icons.Default.Numbers,
            onClick = { /* TODO: Show number picker */ }
        )
        
        PreferenceItem(
            title = "Concurrent fragments",
            summary = "1",
            icon = Icons.Default.Memory,
            onClick = { /* TODO: Show number picker */ }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        PreferenceCategory("Speed & Limits")
        
        PreferenceItem(
            title = "Limit rate",
            summary = "Not set",
            icon = Icons.Default.Speed,
            onClick = { /* TODO: Show input dialog */ }
        )
        
        PreferenceItem(
            title = "Retries",
            summary = "Not set (default: 10)",
            icon = Icons.Default.Refresh,
            onClick = { /* TODO: Show input dialog */ }
        )
        
        PreferenceItem(
            title = "Fragment retries",
            summary = "Not set (default: 10)",
            onClick = { /* TODO: Show input dialog */ }
        )
        
        SwitchPreference(
            title = "Use Aria2",
            summary = "Use Aria2 as external downloader",
            icon = Icons.Default.NetworkCheck,
            checked = useAria2,
            onCheckedChange = { useAria2 = it }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        PreferenceCategory("Caching")
        
        SwitchPreference(
            title = "Cache downloads",
            summary = "Download to cache first, then move to destination",
            icon = Icons.Default.Storage,
            checked = cacheDownloads,
            onCheckedChange = { cacheDownloads = it }
        )
        
        SwitchPreference(
            title = "Keep cache",
            summary = "Keep fragments after download",
            checked = keepCache,
            onCheckedChange = { keepCache = it }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        PreferenceCategory("Scheduling")
        
        SwitchPreference(
            title = "Use scheduler",
            summary = "Schedule downloads for specific time",
            icon = Icons.Default.Schedule,
            checked = useScheduler,
            onCheckedChange = { useScheduler = it }
        )
        
        PreferenceItem(
            title = "Prevent duplicate downloads",
            summary = "Not set",
            icon = Icons.Default.DoNotDisturb,
            onClick = { /* TODO: Show picker dialog */ }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        PreferenceCategory("Privacy")
        
        SwitchPreference(
            title = "Incognito",
            summary = "Don't save download history",
            icon = Icons.Default.VisibilityOff,
            checked = incognito,
            onCheckedChange = { incognito = it }
        )
        
        SwitchPreference(
            title = "Log downloads",
            summary = "Keep download logs",
            checked = logDownloads,
            onCheckedChange = { logDownloads = it }
        )
    }
}
