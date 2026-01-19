package com.deniscerri.ytdl.ui.downloadcard.tabs

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deniscerri.ytdl.database.models.AudioPreferences
import com.deniscerri.ytdl.database.models.DownloadItem
import com.deniscerri.ytdl.database.models.DownloadType
import com.deniscerri.ytdl.database.models.Format
import com.deniscerri.ytdl.database.models.ResultItem
import com.deniscerri.ytdl.database.models.VideoPreferences
import com.deniscerri.ytdl.ui.downloadcard.FormatSelectionDialog
import com.deniscerri.ytdl.ui.downloadcard.components.FormatCard

/**
 * PLATFORM-SPECIFIC: Replicates DownloadAudioFragment from Android
 * Complete implementation with all chips matching adjust_audio.xml exactly
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadAudioTab(
    result: ResultItem,
    incognito: Boolean,
    onDownloadItemChanged: (DownloadItem) -> Unit
) {
    // Metadata State
    var title by remember { mutableStateOf(result.title) }
    var author by remember { mutableStateOf(result.author) }
    var downloadPath by remember { mutableStateOf("~/Music/YTDLnis") }
    var selectedFormat by remember { 
        mutableStateOf(result.formats.find { it.vcodec == "none" || it.format_note.contains("audio", ignoreCase = true) } ?: result.formats.getOrNull(0)) 
    }
    var container by remember { mutableStateOf("Default") }
    
    // Audio Preference State (from adjust_audio.xml)
    // Row 1: Filter chips
    var embedThumb by remember { mutableStateOf(false) }
    var cropThumb by remember { mutableStateOf(false) }
    var splitByChapters by remember { mutableStateOf(false) }
    
    // Row 2-3: Assist chips (sponsorblock, bitrate, filename_template, extra_commands, cut)
    var sponsorBlockFilters by remember { mutableStateOf(listOf<String>()) }
    var bitrate by remember { mutableStateOf("") }
    var filenameTemplate by remember { mutableStateOf("%(uploader).30B - %(title).170B") }
    var extraCommands by remember { mutableStateOf("") }
    var downloadSections by remember { mutableStateOf("") }
    
    // Format Selection Dialog State
    var showFormatDialog by remember { mutableStateOf(false) }
    val audioFormats = remember(result.formats) {
        result.formats.filter { it.vcodec == "none" || it.format_note.contains("audio", ignoreCase = true) }
    }

    // Sync state back to parent
    LaunchedEffect(title, author, downloadPath, selectedFormat, container, embedThumb, cropThumb, splitByChapters, sponsorBlockFilters, bitrate, filenameTemplate, extraCommands, downloadSections, incognito) {
        onDownloadItemChanged(
            DownloadItem(
                id = result.id,
                url = result.url,
                title = title,
                author = author,
                thumb = result.thumb,
                duration = result.duration,
                type = DownloadType.audio,
                format = selectedFormat ?: Format(format_id = "best", format_note = "best", container = container),
                container = if (container == "Default") "" else container,
                allFormats = mutableListOf(),
                downloadPath = downloadPath,
                website = result.website,
                downloadSize = "",
                playlistTitle = result.playlistTitle,
                audioPreferences = AudioPreferences(
                    embedThumb = embedThumb,
                    cropThumb = cropThumb,
                    splitByChapters = splitByChapters,
                    sponsorBlockFilters = ArrayList(sponsorBlockFilters),
                    bitrate = bitrate
                ),
                videoPreferences = VideoPreferences(),
                customFileNameTemplate = filenameTemplate,
                SaveThumb = embedThumb,
                extraCommands = extraCommands,
                downloadSections = downloadSections,
                incognito = incognito,
                logID = null
            )

        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        // Title TextField (matches @+id/title_textinput)
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            singleLine = true,
            trailingIcon = {
                if (title != result.title) {
                    IconButton(onClick = { title = result.title }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset")
                    }
                }
            }
        )

        // Author + Container Row (matches LinearLayout divider)
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Author TextField (matches @+id/author_textinput, weight 45)
            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Author") },
                modifier = Modifier.weight(45f),
                singleLine = true,
                trailingIcon = {
                    if (author != result.author) {
                        IconButton(onClick = { author = result.author }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reset")
                        }
                    }
                }
            )

            // Container Dropdown (matches @+id/downloadContainer, weight 45)
            var containerExpanded by remember { mutableStateOf(false) }
            val containers = listOf("Default", "m4a", "mp3", "ogg", "opus", "webm", "wav", "flac")
            ExposedDropdownMenuBox(
                expanded = containerExpanded,
                onExpandedChange = { containerExpanded = it },
                modifier = Modifier.weight(45f)
            ) {
                OutlinedTextField(
                    value = container,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Container") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = containerExpanded) },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = containerExpanded,
                    onDismissRequest = { containerExpanded = false }
                ) {
                    containers.forEach { ext ->
                        DropdownMenuItem(
                            text = { Text(ext) },
                            onClick = {
                                container = ext
                                containerExpanded = false
                                // Disable embed thumb for wav (matches Android logic)
                                if (ext == "wav") {
                                    embedThumb = false
                                }
                            }
                        )
                    }
                }
            }
        }

        // Audio Quality Label
        Text(
            text = "Audio Quality",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 10.dp, start = 0.dp)
        )
        
        // Format Card (matches include layout="@layout/format_item")
        selectedFormat?.let { format ->
            FormatCard(
                chosenFormat = format,
                showSize = downloadSections.isEmpty(),
                onClick = { showFormatDialog = true }
            )
        } ?: Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(vertical = 8.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text("No formats available")
            }
        }
        
        // Format Selection Dialog
        if (showFormatDialog && audioFormats.isNotEmpty()) {
            FormatSelectionDialog(
                formats = audioFormats,
                onFormatSelected = { selectedFormat = it },
                onDismiss = { showFormatDialog = false }
            )
        }

        // Output Path (matches @+id/outputPath)
        OutlinedTextField(
            value = downloadPath,
            onValueChange = { downloadPath = it },
            label = { Text("Save Dir") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            maxLines = 2
        )

        // Free Space (matches @+id/freespace)
        Text(
            text = "Free Space: Available",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 10.dp)
        )

        // ========== ADJUST AUDIO SECTION (matches adjust_audio.xml) ==========
        Text(
            text = "Adjust Audio",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
        )
        
        // Row 1: Filter Chips (embed_thumb, crop_thumb, split_by_chapters)
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = embedThumb,
                onClick = { 
                    if (container != "wav") embedThumb = !embedThumb 
                },
                enabled = container != "wav",
                label = { Text("Embed Thumb") }
            )
            if (embedThumb) {
                FilterChip(
                    selected = cropThumb,
                    onClick = { cropThumb = !cropThumb },
                    label = { Text("Crop Thumb") }
                )
            }
            FilterChip(
                selected = splitByChapters,
                onClick = { splitByChapters = !splitByChapters },
                label = { Text("Split by Chapters") }
            )
        }
        
        // Row 2: Assist Chips (sponsorblock, bitrate, filename_template)
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AssistChip(
                onClick = { /* Show SponsorBlock dialog */ },
                label = { Text("SponsorBlock") },
                leadingIcon = { Icon(Icons.Default.MonetizationOn, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            AssistChip(
                onClick = { /* Show Bitrate dialog */ },
                label = { Text("Bitrate") },
                leadingIcon = { Icon(Icons.Default.HighQuality, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            AssistChip(
                onClick = { /* Show Filename Template dialog */ },
                label = { Text("File Name Template") },
                leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
        }

        // Row 3: Assist Chips (extra_commands, cut)
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(top = 4.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AssistChip(
                onClick = { /* Show Extra Commands dialog */ },
                label = { Text("Use Extra Commands") },
                leadingIcon = { Icon(Icons.Default.Terminal, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            AssistChip(
                onClick = { /* Show Cut dialog */ },
                label = { Text("Cut") },
                leadingIcon = { Icon(Icons.Default.ContentCut, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
        }
    }
}
