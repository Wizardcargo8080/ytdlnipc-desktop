package com.deniscerri.ytdl.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Folders Settings - matching Android folders_preferences.xml
 */
@Composable
fun FoldersSettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        PreferenceCategory("Download Locations")
        
        PreferenceItem(
            title = "Music folder",
            summary = "~/Music/YTDLnis",
            icon = Icons.Default.MusicNote,
            onClick = { /* TODO: Open folder picker */ }
        )
        
        PreferenceItem(
            title = "Video folder",
            summary = "~/Videos/YTDLnis",
            icon = Icons.Default.VideoFile,
            onClick = { /* TODO: Open folder picker */ }
        )
        
        PreferenceItem(
            title = "Command folder",
            summary = "~/Downloads/YTDLnis",
            icon = Icons.Default.Terminal,
            onClick = { /* TODO: Open folder picker */ }
        )
        
        PreferenceCategory("Other")
        
        PreferenceItem(
            title = "Cache directory",
            summary = "App data folder",
            icon = Icons.Default.Storage,
            onClick = { /* TODO: Open folder picker */ }
        )
        
        PreferenceItem(
            title = "Download archive",
            summary = "Prevent re-downloading same videos",
            icon = Icons.Default.Archive,
            onClick = { /* TODO: Open folder picker */ }
        )
    }
}
