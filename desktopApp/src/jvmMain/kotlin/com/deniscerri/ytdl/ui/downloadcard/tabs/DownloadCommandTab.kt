package com.deniscerri.ytdl.ui.downloadcard.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deniscerri.ytdl.database.models.CommandTemplate
import com.deniscerri.ytdl.database.models.DownloadItem
import com.deniscerri.ytdl.database.models.DownloadType
import com.deniscerri.ytdl.database.models.ResultItem
import com.deniscerri.ytdl.database.models.Format
import com.deniscerri.ytdl.database.models.AudioPreferences
import com.deniscerri.ytdl.database.models.VideoPreferences


/**
 * PLATFORM-SPECIFIC: Replicates DownloadCommandFragment from Android
 */
@Composable
fun DownloadCommandTab(
    result: ResultItem,
    onDownloadItemChanged: (DownloadItem) -> Unit
) {
    // Placeholder list of templates - in real app, fetch from database
    val templates = remember { 
        listOf(
            CommandTemplate(1, "Default", "yt-dlp --newline --progress"),
            CommandTemplate(2, "Best Quality", "yt-dlp -f bestvideo+bestaudio")
        )
    }
    
    var selectedTemplate by remember { mutableStateOf(templates.firstOrNull()) }

    LaunchedEffect(selectedTemplate) {
        selectedTemplate?.let { template ->
            onDownloadItemChanged(
                DownloadItem(
                    id = result.id,
                    url = result.url,
                    title = result.title,
                    author = result.author,
                    thumb = result.thumb,
                    duration = result.duration,
                    type = DownloadType.command,
                    format = Format(format_id = "best", format_note = "best", container = ""),
                    allFormats = mutableListOf(),
                    downloadPath = "",
                    website = result.website,
                    downloadSize = "",
                    playlistTitle = result.playlistTitle,
                    audioPreferences = AudioPreferences(),
                    videoPreferences = VideoPreferences(),
                    customFileNameTemplate = "",
                    SaveThumb = false,
                    extraCommands = template.content,
                    logID = null
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Command Templates", style = MaterialTheme.typography.labelMedium)
        
        if (templates.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.List, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("No templates found", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Button(onClick = { /* TODO: Navigate to template creation */ }, modifier = Modifier.padding(top = 16.dp)) {
                        Text("ADD TEMPLATE FIRST")
                    }
                }
            }
        } else {
            templates.forEach { template ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    onClick = { selectedTemplate = template },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedTemplate?.id == template.id) 
                            MaterialTheme.colorScheme.primaryContainer 
                        else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Terminal, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(template.title, style = MaterialTheme.typography.titleMedium)
                            Text(template.content, style = MaterialTheme.typography.bodySmall, maxLines = 1)
                        }
                    }
                }
            }
        }
    }
}
