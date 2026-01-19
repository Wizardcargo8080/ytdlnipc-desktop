package com.deniscerri.ytdl.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class DownloadTab(val title: String) {
    Active("Active"),
    Queued("Queued"),
    Cancelled("Cancelled"),
    Errored("Errored"),
    Saved("Saved"),
    Scheduled("Scheduled")
}

@Composable
fun DownloadsScreen() {
    var selectedTab by remember { mutableStateOf(DownloadTab.Active) }
    
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            modifier = Modifier.fillMaxWidth()
        ) {
            DownloadTab.values().forEach { tab ->
                Tab(
                    selected = selectedTab == tab,
                    onClick = { selectedTab = tab },
                    text = { Text(tab.title) }
                )
            }
        }
        
        when (selectedTab) {
            DownloadTab.Active -> ActiveDownloadsTab()
            DownloadTab.Queued -> QueuedDownloadsTab()
            DownloadTab.Cancelled -> CancelledDownloadsTab()
            DownloadTab.Errored -> ErroredDownloadsTab()
            DownloadTab.Saved -> SavedDownloadsTab()
            DownloadTab.Scheduled -> ScheduledDownloadsTab()
        }
    }
}

@Composable
private fun ActiveDownloadsTab() {
    EmptyDownloadsList("No active downloads")
}

@Composable
private fun QueuedDownloadsTab() {
    EmptyDownloadsList("Queue is empty")
}

@Composable
private fun CancelledDownloadsTab() {
    EmptyDownloadsList("No cancelled downloads")
}

@Composable
private fun ErroredDownloadsTab() {
    EmptyDownloadsList("No errored downloads")
}

@Composable
private fun SavedDownloadsTab() {
    EmptyDownloadsList("No saved downloads")
}

@Composable
private fun ScheduledDownloadsTab() {
    EmptyDownloadsList("No scheduled downloads")
}

@Composable
private fun EmptyDownloadsList(message: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}
