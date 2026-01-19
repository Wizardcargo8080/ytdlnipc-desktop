package com.deniscerri.ytdl.ui.screens

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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MoreScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "More",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Menu items matching Android MoreFragment
        MoreMenuItem(
            icon = Icons.Default.Terminal,
            title = "Terminal",
            subtitle = "Run custom yt-dlp commands",
            onClick = { /* TODO: Open terminal */ }
        )
        
        MoreMenuItem(
            icon = Icons.Default.Code,
            title = "Command templates",
            subtitle = "Manage command templates",
            onClick = { /* TODO: Open command templates */ }
        )
        
        MoreMenuItem(
            icon = Icons.Default.Cookie,
            title = "Cookies",
            subtitle = "Manage browser cookies",
            onClick = { /* TODO: Open cookies */ }
        )
        
        MoreMenuItem(
            icon = Icons.Default.Visibility,
            title = "Observe sources",
            subtitle = "Monitor URLs for new content",
            onClick = { /* TODO: Open observe sources */ }
        )
        
        MoreMenuItem(
            icon = Icons.Default.Notifications,
            title = "Logs",
            subtitle = "View download logs",
            onClick = { /* TODO: Open logs */ }
        )
        
        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        
        MoreMenuItem(
            icon = Icons.Default.Settings,
            title = "Settings",
            subtitle = "App preferences",
            onClick = { /* TODO: Open settings */ }
        )
        
        MoreMenuItem(
            icon = Icons.Default.Info,
            title = "About",
            subtitle = "YTDLnis Desktop v1.0.0",
            onClick = { /* TODO: Open about */ }
        )
    }
}

@Composable
private fun MoreMenuItem(
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
