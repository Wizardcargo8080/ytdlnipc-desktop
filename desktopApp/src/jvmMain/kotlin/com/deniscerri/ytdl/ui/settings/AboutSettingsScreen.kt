package com.deniscerri.ytdl.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * About Settings - matching Android about_preferences.xml
 */
@Composable
fun AboutSettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "YTDLnis Desktop",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        
        Text(
            text = "Version 1.0.0",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "A powerful video/audio downloader",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        PreferenceCategory("Links")
        
        PreferenceItem(
            title = "GitHub",
            summary = "View source code and report issues",
            icon = Icons.Default.Code,
            onClick = { /* TODO: Open URL */ }
        )
        
        PreferenceItem(
            title = "yt-dlp",
            summary = "Powered by yt-dlp",
            icon = Icons.Default.Link,
            onClick = { /* TODO: Open URL */ }
        )
        
        PreferenceItem(
            title = "Translations",
            summary = "Help translate the app",
            icon = Icons.Default.Translate,
            onClick = { /* TODO: Open URL */ }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        PreferenceCategory("Legal")
        
        PreferenceItem(
            title = "Open source licenses",
            summary = "View third-party licenses",
            icon = Icons.Default.Description,
            onClick = { /* TODO: Show licenses */ }
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "© 2024 Denis Cerri",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
