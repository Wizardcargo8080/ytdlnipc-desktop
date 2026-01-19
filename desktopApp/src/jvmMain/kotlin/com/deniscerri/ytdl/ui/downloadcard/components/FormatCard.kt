package com.deniscerri.ytdl.ui.downloadcard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deniscerri.ytdl.database.models.Format
import com.deniscerri.ytdl.util.FileUtil

/**
 * CORE logic ported from UiUtil.populateFormatCard
 * Replicates the Android MaterialCardView layout for format details
 */
@Composable
fun FormatCard(
    chosenFormat: Format,
    audioFormats: List<Format>? = null,
    showSize: Boolean = true,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Container and Note
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = (chosenFormat.container.takeIf { it.isNotBlank() && it != "Default" } ?: "DEFAULT").uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = getFormatNote(chosenFormat).uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // File Size
                Text(
                    text = if (showSize) {
                        var filesize = chosenFormat.filesize
                        if (!audioFormats.isNullOrEmpty() && filesize > 10L) {
                            filesize += audioFormats.sumOf { it.filesize }
                        }
                        FileUtil.convertFileSize(filesize)
                    } else "?",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Format ID and Codec
                Column {
                    Text(
                        text = "id: ${chosenFormat.format_id}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    val codec = getCodecString(chosenFormat)
                    if (codec.isNotBlank() && codec != "none") {
                        Text(
                            text = codec.uppercase(),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Audio Formats / TBR
                Column(horizontalAlignment = Alignment.End) {
                    if (!audioFormats.isNullOrEmpty()) {
                        Text(
                            text = "id: " + audioFormats.joinToString("+") { it.format_id },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else if (chosenFormat.vcodec != "none" && chosenFormat.vcodec != "" && 
                               chosenFormat.acodec != "none" && chosenFormat.acodec != "") {
                        Text(
                            text = chosenFormat.acodec.uppercase(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (!chosenFormat.tbr.isNullOrBlank() && (chosenFormat.vcodec.isBlank() || chosenFormat.vcodec == "none")) {
                        Text(
                            text = chosenFormat.tbr ?: "",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

private fun getFormatNote(format: Format): String {
    return when (format.format_note) {
        "" -> "Default"
        "best" -> "Best Quality"
        "worst" -> "Worst Quality"
        else -> format.format_note
    }
}

private fun getCodecString(format: Format): String {
    return if (format.encoding != "") {
        format.encoding
    } else if (format.vcodec != "none" && format.vcodec != "") {
        format.vcodec
    } else {
        format.acodec
    }
}
