package com.deniscerri.ytdl.ui.downloadcard

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.deniscerri.ytdl.database.models.DownloadItem
import com.deniscerri.ytdl.database.models.DownloadType
import com.deniscerri.ytdl.database.models.ResultItem
import com.deniscerri.ytdl.ui.downloadcard.tabs.DownloadAudioTab
import com.deniscerri.ytdl.ui.downloadcard.tabs.DownloadVideoTab
import com.deniscerri.ytdl.ui.downloadcard.tabs.DownloadCommandTab

/**
 * PLATFORM-SPECIFIC: Replicates DownloadBottomSheetDialog from Android
 * Main entry point for download configuration
 */
@Composable
fun DownloadCardDialog(
    result: ResultItem,
    initialType: DownloadType = DownloadType.video,
    onDismiss: () -> Unit,
    onDownload: (DownloadItem) -> Unit,
    onSchedule: (DownloadItem) -> Unit,
    onSaveForLater: (DownloadItem) -> Unit
) {
    var selectedTab by remember { mutableStateOf(initialType) }
    var incognito by remember { mutableStateOf(false) }
    
    // Lifted state for the tabs to sync (like GUISync in Android)
    var currentDownloadItem by remember { mutableStateOf<DownloadItem?>(null) }
    
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.9f),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp
        ) {
            Scaffold(
                topBar = {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = result.title.ifEmpty { "Loading..." },
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 1
                                )
                                Text(
                                    text = result.author.ifEmpty { result.url },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1
                                )
                            }
                            IconButton(onClick = onDismiss) {
                                Icon(Icons.Default.Close, contentDescription = "Close")
                            }
                        }
                        
                        TabRow(
                            selectedTabIndex = when(selectedTab) {
                                DownloadType.audio -> 0
                                DownloadType.video -> 1
                                DownloadType.command -> 2
                                else -> 1 // Default to video for auto
                            }
                        ) {
                            Tab(
                                selected = selectedTab == DownloadType.audio,
                                onClick = { selectedTab = DownloadType.audio },
                                text = { Text("AUDIO") }
                            )
                            Tab(
                                selected = selectedTab == DownloadType.video,
                                onClick = { selectedTab = DownloadType.video },
                                text = { Text("VIDEO") }
                            )
                            Tab(
                                selected = selectedTab == DownloadType.command,
                                onClick = { selectedTab = DownloadType.command },
                                text = { Text("COMMAND") }
                            )
                        }
                    }
                },
                bottomBar = {
                    Surface(
                        tonalElevation = 4.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Incognito Toggle
                            IconButton(
                                onClick = { incognito = !incognito },
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = if (incognito) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                                )
                            ) {
                                Icon(Icons.Default.VisibilityOff, contentDescription = "Incognito")
                            }
                            
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedButton(
                                    onClick = { currentDownloadItem?.let { onSchedule(it) } },
                                    contentPadding = PaddingValues(horizontal = 16.dp)
                                ) {
                                    Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("SCHEDULE")
                                }
                                
                                Button(
                                    onClick = { currentDownloadItem?.let { onDownload(it) } },
                                    contentPadding = PaddingValues(horizontal = 16.dp)
                                ) {
                                    Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("DOWNLOAD")
                                }
                            }
                        }
                    }
                }
            ) { padding ->
                Box(modifier = Modifier.padding(padding).fillMaxSize()) {
                    when (selectedTab) {
                        DownloadType.audio -> DownloadAudioTab(
                            result = result,
                            incognito = incognito,
                            onDownloadItemChanged = { currentDownloadItem = it }
                        )
                        DownloadType.video -> DownloadVideoTab(
                            result = result,
                            incognito = incognito,
                            onDownloadItemChanged = { currentDownloadItem = it }
                        )
                        DownloadType.command -> DownloadCommandTab(
                            result = result,
                            onDownloadItemChanged = { currentDownloadItem = it }
                        )
                        else -> DownloadVideoTab(
                            result = result,
                            incognito = incognito,
                            onDownloadItemChanged = { currentDownloadItem = it }
                        )
                    }
                }
            }
        }
    }
}
