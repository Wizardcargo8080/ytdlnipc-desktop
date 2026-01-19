package com.deniscerri.ytdl.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Router
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Advanced Settings - matching Android advanced_preferences.xml
 */
@Composable
fun AdvancedSettingsScreen() {
    var useCookies by remember { mutableStateOf(false) }
    var forceIpv4 by remember { mutableStateOf(false) }
    var noCheckCertificates by remember { mutableStateOf(false) }
    var useAppLanguageForMetadata by remember { mutableStateOf(true) }
    var disableWriteInfoJson by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        PreferenceCategory("Cookies & Authentication")
        
        SwitchPreference(
            title = "Use cookies",
            summary = "Use browser cookies for authentication",
            icon = Icons.Default.Cookie,
            checked = useCookies,
            onCheckedChange = { useCookies = it }
        )
        
        PreferenceItem(
            title = "User-Agent header",
            summary = "Not set",
            icon = Icons.Default.PersonOutline,
            onClick = { /* TODO: Show input dialog */ }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        PreferenceCategory("Network")
        
        PreferenceItem(
            title = "Proxy",
            summary = "Not set",
            icon = Icons.Default.Router,
            onClick = { /* TODO: Show input dialog */ }
        )
        
        SwitchPreference(
            title = "Force IPv4",
            summary = "Make all connections via IPv4",
            icon = Icons.Default.Wifi,
            checked = forceIpv4,
            onCheckedChange = { forceIpv4 = it }
        )
        
        PreferenceItem(
            title = "Socket timeout",
            summary = "5 seconds",
            icon = Icons.Default.Timer,
            onClick = { /* TODO: Show input dialog */ }
        )
        
        SwitchPreference(
            title = "No check certificates",
            summary = "Disable HTTPS certificate validation",
            icon = Icons.Default.Lock,
            checked = noCheckCertificates,
            onCheckedChange = { noCheckCertificates = it }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        PreferenceCategory("Metadata & Extraction")
        
        SwitchPreference(
            title = "Use app language for metadata",
            summary = "Extract metadata in app language when possible",
            icon = Icons.Default.Language,
            checked = useAppLanguageForMetadata,
            onCheckedChange = { useAppLanguageForMetadata = it }
        )
        
        SwitchPreference(
            title = "Disable write info JSON",
            summary = "Don't write .info.json files",
            checked = disableWriteInfoJson,
            onCheckedChange = { disableWriteInfoJson = it }
        )
        
        PreferenceItem(
            title = "YouTube player clients",
            summary = "Configure YouTube extraction",
            onClick = { /* TODO: Show config dialog */ }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        PreferenceCategory("Extra Commands")
        
        PreferenceItem(
            title = "Extra command",
            summary = "Additional yt-dlp arguments",
            icon = Icons.Default.Code,
            onClick = { /* TODO: Show input dialog */ }
        )
    }
}
