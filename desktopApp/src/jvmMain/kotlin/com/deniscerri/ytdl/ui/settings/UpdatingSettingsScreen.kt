package com.deniscerri.ytdl.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Update
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Updating Settings - matching Android updating_preferences.xml
 */
@Composable
fun UpdatingSettingsScreen() {
    var autoUpdateYtdlp by remember { mutableStateOf(false) }
    var updateYtdlpWhileDownloading by remember { mutableStateOf(false) }
    var automaticBackup by remember { mutableStateOf(true) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        PreferenceCategory("yt-dlp")
        
        PreferenceItem(
            title = "Update yt-dlp",
            summary = "Check for yt-dlp updates now",
            icon = Icons.Default.Refresh,
            onClick = { /* TODO: Trigger yt-dlp update */ }
        )
        
        SwitchPreference(
            title = "Auto-update yt-dlp",
            summary = "Automatically update yt-dlp on startup",
            icon = Icons.Default.Update,
            checked = autoUpdateYtdlp,
            onCheckedChange = { autoUpdateYtdlp = it }
        )
        
        PreferenceItem(
            title = "yt-dlp source",
            summary = "stable",
            icon = Icons.Default.CloudDownload,
            onClick = { /* TODO: Show source picker */ }
        )
        
        SwitchPreference(
            title = "Update while downloading",
            summary = "Allow yt-dlp updates during active downloads",
            checked = updateYtdlpWhileDownloading,
            onCheckedChange = { updateYtdlpWhileDownloading = it }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        PreferenceCategory("Backup")
        
        SwitchPreference(
            title = "Automatic backup",
            summary = "Automatically backup settings",
            icon = Icons.Default.Backup,
            checked = automaticBackup,
            onCheckedChange = { automaticBackup = it }
        )
        
        PreferenceItem(
            title = "Backup now",
            summary = "Create a backup of current settings",
            onClick = { /* TODO: Trigger backup */ }
        )
        
        PreferenceItem(
            title = "Restore backup",
            summary = "Restore settings from a backup file",
            onClick = { /* TODO: Show file picker */ }
        )
    }
}
