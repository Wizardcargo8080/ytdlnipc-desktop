package com.deniscerri.ytdl.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AudioFile
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Processing Settings - matching Android processing_preferences.xml
 */
@Composable
fun ProcessingSettingsScreen() {
    // Audio preferences
    var embedThumbnail by remember { mutableStateOf(false) }
    var cropThumbnail by remember { mutableStateOf(false) }
    
    // Video preferences
    var embedSubtitles by remember { mutableStateOf(false) }
    var writeSubtitles by remember { mutableStateOf(false) }
    var writeAutoSubtitles by remember { mutableStateOf(false) }
    var addChapters by remember { mutableStateOf(false) }
    var recodeVideo by remember { mutableStateOf(false) }
    var videoEmbedThumbnail by remember { mutableStateOf(false) }
    
    // General preferences
    var writeThumbnail by remember { mutableStateOf(false) }
    var writeDescription by remember { mutableStateOf(false) }
    var embedMetadata by remember { mutableStateOf(true) }
    var restrictFilenames by remember { mutableStateOf(true) }
    var enableMtime by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        PreferenceCategory("Audio")
        
        PreferenceItem(
            title = "Audio format",
            summary = "Default",
            icon = Icons.Default.AudioFile,
            onClick = { /* TODO: Show format picker */ }
        )
        
        PreferenceItem(
            title = "Audio quality",
            summary = "Best",
            onClick = { /* TODO: Show quality picker */ }
        )
        
        PreferenceItem(
            title = "Audio filename template",
            summary = "%(uploader).30B - %(title).170B",
            icon = Icons.Default.TextFields,
            onClick = { /* TODO: Show input dialog */ }
        )
        
        SwitchPreference(
            title = "Embed thumbnail",
            summary = "Embed album art in audio files",
            icon = Icons.Default.Image,
            checked = embedThumbnail,
            onCheckedChange = { embedThumbnail = it }
        )
        
        SwitchPreference(
            title = "Crop thumbnail",
            summary = "Crop thumbnail to square",
            icon = Icons.Default.Crop,
            checked = cropThumbnail,
            onCheckedChange = { cropThumbnail = it }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        PreferenceCategory("Video")
        
        PreferenceItem(
            title = "Video format",
            summary = "Default",
            icon = Icons.Default.VideoFile,
            onClick = { /* TODO: Show format picker */ }
        )
        
        PreferenceItem(
            title = "Video quality",
            summary = "Best",
            onClick = { /* TODO: Show quality picker */ }
        )
        
        PreferenceItem(
            title = "Video filename template",
            summary = "%(uploader).30B - %(title).170B",
            icon = Icons.Default.TextFields,
            onClick = { /* TODO: Show input dialog */ }
        )
        
        SwitchPreference(
            title = "Embed subtitles",
            summary = "Embed subtitles in video file",
            icon = Icons.Default.Subtitles,
            checked = embedSubtitles,
            onCheckedChange = { embedSubtitles = it }
        )
        
        SwitchPreference(
            title = "Write subtitles",
            summary = "Save subtitles as separate file",
            checked = writeSubtitles,
            onCheckedChange = { writeSubtitles = it }
        )
        
        SwitchPreference(
            title = "Write auto subtitles",
            summary = "Include auto-generated subtitles",
            checked = writeAutoSubtitles,
            onCheckedChange = { writeAutoSubtitles = it }
        )
        
        PreferenceItem(
            title = "Subtitle languages",
            summary = "en.*,.*-orig",
            onClick = { /* TODO: Show input dialog */ }
        )
        
        SwitchPreference(
            title = "Add chapters",
            summary = "Embed chapters from video",
            icon = Icons.Default.VideoLibrary,
            checked = addChapters,
            onCheckedChange = { addChapters = it }
        )
        
        SwitchPreference(
            title = "Recode video",
            summary = "Re-encode video to specified format",
            checked = recodeVideo,
            onCheckedChange = { recodeVideo = it }
        )
        
        SwitchPreference(
            title = "Embed thumbnail in video",
            summary = "Add cover art to video files",
            icon = Icons.Default.Image,
            checked = videoEmbedThumbnail,
            onCheckedChange = { videoEmbedThumbnail = it }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        PreferenceCategory("SponsorBlock")
        
        PreferenceItem(
            title = "SponsorBlock filters",
            summary = "Non-music sections",
            icon = Icons.Default.SkipNext,
            onClick = { /* TODO: Show multi-select dialog */ }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        PreferenceCategory("General")
        
        SwitchPreference(
            title = "Write thumbnail",
            summary = "Save thumbnail as separate file",
            icon = Icons.Default.Image,
            checked = writeThumbnail,
            onCheckedChange = { writeThumbnail = it }
        )
        
        SwitchPreference(
            title = "Write description",
            summary = "Save video description as text file",
            icon = Icons.Default.Description,
            checked = writeDescription,
            onCheckedChange = { writeDescription = it }
        )
        
        SwitchPreference(
            title = "Embed metadata",
            summary = "Embed media metadata in file",
            checked = embedMetadata,
            onCheckedChange = { embedMetadata = it }
        )
        
        SwitchPreference(
            title = "Restrict filenames",
            summary = "Use only ASCII characters in filenames",
            checked = restrictFilenames,
            onCheckedChange = { restrictFilenames = it }
        )
        
        SwitchPreference(
            title = "Use mtime",
            summary = "Set file modification time to upload date",
            checked = enableMtime,
            onCheckedChange = { enableMtime = it }
        )
    }
}
