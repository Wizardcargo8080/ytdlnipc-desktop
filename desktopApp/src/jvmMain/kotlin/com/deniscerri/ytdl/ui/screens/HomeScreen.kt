package com.deniscerri.ytdl.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.deniscerri.ytdl.database.models.DownloadItem
import com.deniscerri.ytdl.database.models.DownloadType
import com.deniscerri.ytdl.database.models.ResultItem
import com.deniscerri.ytdl.ui.downloadcard.DownloadCardDialog

@Composable
fun HomeScreen() {
    var searchQuery by remember { mutableStateOf("") }
    val results = remember { mutableStateListOf<ResultItem>() }
    var showDownloadDialog by remember { mutableStateOf<ResultItem?>(null) }
    var initialDownloadType by remember { mutableStateOf(DownloadType.video) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search bar - identical layout to Android HomeFragment
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Search or insert URL") },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    IconButton(onClick = {
                        // TODO: Paste from clipboard
                    }) {
                        Icon(Icons.Default.ContentPaste, contentDescription = "Paste")
                    }
                }
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Button(
                onClick = {
                    // TODO: Trigger search/fetch
                }
            ) {
                Text("Search")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Results list
        if (results.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No results",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn {
                items(results) { item ->
                    ResultCard(
                        item = item,
                        onClick = { 
                            initialDownloadType = DownloadType.video
                            showDownloadDialog = item 
                        },
                        onDownloadClick = { type ->
                            initialDownloadType = type
                            showDownloadDialog = item
                        }
                    )
                }
            }
        }
    }

    // Download Dialog Integration
    showDownloadDialog?.let { result ->
        DownloadCardDialog(
            result = result,
            initialType = initialDownloadType,
            onDismiss = { showDownloadDialog = null },
            onDownload = { downloadItem ->
                // TODO: Transfer to DownloadManager
                showDownloadDialog = null
            },
            onSchedule = { downloadItem ->
                // TODO: Handle scheduling
                showDownloadDialog = null
            },
            onSaveForLater = { downloadItem ->
                // TODO: Save to database
                showDownloadDialog = null
            }
        )
    }
}

@Composable
fun ResultCard(
    item: ResultItem,
    onClick: () -> Unit,
    onDownloadClick: (DownloadType) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail placeholder
            Card(
                modifier = Modifier
                    .width(120.dp)
                    .height(68.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = item.author,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = item.duration,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row {
                IconButton(onClick = { onDownloadClick(DownloadType.audio) }) {
                    Icon(Icons.Default.MusicNote, contentDescription = "Download Audio")
                }
                IconButton(onClick = { onDownloadClick(DownloadType.video) }) {
                    Icon(Icons.Default.VideoLibrary, contentDescription = "Download Video")
                }
            }
        }
    }
}
