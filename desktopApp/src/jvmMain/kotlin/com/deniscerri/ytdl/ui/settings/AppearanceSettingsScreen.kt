package com.deniscerri.ytdl.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Appearance Settings - matching Android appearance_preferences.xml
 */
@Composable
fun AppearanceSettingsScreen() {
    var theme by remember { mutableStateOf("System") }
    var highContrast by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        PreferenceCategory("Theme")
        
        PreferenceItem(
            title = "Theme",
            summary = theme,
            icon = Icons.Default.DarkMode,
            onClick = { /* TODO: Show theme picker dialog */ }
        )
        
        PreferenceItem(
            title = "Accent",
            summary = "Material You",
            icon = Icons.Default.Palette,
            onClick = { /* TODO: Show accent picker dialog */ }
        )
        
        SwitchPreference(
            title = "High contrast",
            summary = "Use high contrast colors",
            icon = Icons.Default.Contrast,
            checked = highContrast,
            onCheckedChange = { highContrast = it }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        PreferenceCategory("Language")
        
        PreferenceItem(
            title = "Language",
            summary = "System",
            icon = Icons.Default.Language,
            onClick = { /* TODO: Show language picker dialog */ }
        )
    }
}
