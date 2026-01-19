package com.deniscerri.ytdl.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

enum class SettingsScreen {
    Main, Appearance, Folders, Downloading, Processing, Updating, Advanced, About
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsNavigation(onBack: () -> Unit) {
    var currentScreen by remember { mutableStateOf(SettingsScreen.Main) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (currentScreen) {
                            SettingsScreen.Main -> "Settings"
                            SettingsScreen.Appearance -> "Appearance"
                            SettingsScreen.Folders -> "Folders"
                            SettingsScreen.Downloading -> "Downloading"
                            SettingsScreen.Processing -> "Processing"
                            SettingsScreen.Updating -> "Updating"
                            SettingsScreen.Advanced -> "Advanced"
                            SettingsScreen.About -> "About"
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (currentScreen == SettingsScreen.Main) {
                            onBack()
                        } else {
                            currentScreen = SettingsScreen.Main
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (currentScreen) {
                SettingsScreen.Main -> MainSettingsScreen { currentScreen = it }
                SettingsScreen.Appearance -> AppearanceSettingsScreen()
                SettingsScreen.Folders -> FoldersSettingsScreen()
                SettingsScreen.Downloading -> DownloadingSettingsScreen()
                SettingsScreen.Processing -> ProcessingSettingsScreen()
                SettingsScreen.Updating -> UpdatingSettingsScreen()
                SettingsScreen.Advanced -> AdvancedSettingsScreen()
                SettingsScreen.About -> AboutSettingsScreen()
            }
        }
    }
}

@Composable
fun MainSettingsScreen(onNavigate: (SettingsScreen) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        SettingsCategory(
            icon = Icons.Default.Palette,
            title = "Appearance",
            subtitle = "Theme, accent, language",
            onClick = { onNavigate(SettingsScreen.Appearance) }
        )
        
        SettingsCategory(
            icon = Icons.Default.Folder,
            title = "Folders",
            subtitle = "Download locations",
            onClick = { onNavigate(SettingsScreen.Folders) }
        )
        
        SettingsCategory(
            icon = Icons.Default.Download,
            title = "Downloading",
            subtitle = "Download behavior and limits",
            onClick = { onNavigate(SettingsScreen.Downloading) }
        )
        
        SettingsCategory(
            icon = Icons.Default.Tune,
            title = "Processing",
            subtitle = "Audio/video processing options",
            onClick = { onNavigate(SettingsScreen.Processing) }
        )
        
        SettingsCategory(
            icon = Icons.Default.Update,
            title = "Updating",
            subtitle = "App and yt-dlp updates",
            onClick = { onNavigate(SettingsScreen.Updating) }
        )
        
        SettingsCategory(
            icon = Icons.Default.Code,
            title = "Advanced",
            subtitle = "Cookies, proxy, extra commands",
            onClick = { onNavigate(SettingsScreen.Advanced) }
        )
        
        SettingsCategory(
            icon = Icons.Default.Info,
            title = "About",
            subtitle = "Version info and licenses",
            onClick = { onNavigate(SettingsScreen.About) }
        )
    }
}

@Composable
private fun SettingsCategory(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Preference Item Components - matching Android style
@Composable
fun PreferenceItem(
    title: String,
    summary: String? = null,
    icon: ImageVector? = null,
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (summary != null) {
                    Text(
                        text = summary,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            if (trailing != null) {
                trailing()
            }
        }
    }
}

@Composable
fun SwitchPreference(
    title: String,
    summary: String? = null,
    icon: ImageVector? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    PreferenceItem(
        title = title,
        summary = summary,
        icon = icon,
        onClick = { onCheckedChange(!checked) },
        trailing = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    )
}

@Composable
fun PreferenceCategory(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
    )
}
