package com.deniscerri.ytdl.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deniscerri.ytdl.database.models.TerminalItem

/**
 * PLATFORM-SPECIFIC: Replicates TerminalFragment from Android
 */
@Composable
fun TerminalScreen() {
    var command by remember { mutableStateOf("") }
    val terminalEntries = remember { mutableStateListOf<TerminalItem>() }

    Column(modifier = Modifier.fillMaxSize()) {
        // Terminal Output Area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.Black)
                .padding(8.dp)
        ) {
            if (terminalEntries.isEmpty()) {
                Text(
                    text = "YTDLnis Terminal Ready\nEnter yt-dlp commands below...",
                    color = Color.Green,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp
                )
            } else {
                LazyColumn {
                    items(terminalEntries) { entry ->
                        Text(
                            text = "> ${entry.command}",
                            color = Color.Cyan,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp
                        )
                        // In a real implementation, we'd show the process output here
                        Text(
                            text = "Command executed (Simulation)...",
                            color = Color.White,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        // Command Input Area
        Surface(
            tonalElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Terminal,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    if (command.isEmpty()) {
                        Text(
                            "Enter command...",
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    BasicTextField(
                        value = command,
                        onValueChange = { command = it },
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 14.sp
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                IconButton(
                    onClick = {
                        if (command.isNotBlank()) {
                            terminalEntries.add(TerminalItem(0, command))
                            command = ""
                        }
                    },
                    enabled = command.isNotBlank()
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Run")
                }
            }
        }
    }
}
