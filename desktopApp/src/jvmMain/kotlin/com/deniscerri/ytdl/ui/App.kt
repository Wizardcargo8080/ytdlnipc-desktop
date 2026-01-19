package com.deniscerri.ytdl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.deniscerri.ytdl.ui.screens.DownloadsScreen
import com.deniscerri.ytdl.ui.screens.HistoryScreen
import com.deniscerri.ytdl.ui.screens.HomeScreen
import com.deniscerri.ytdl.ui.screens.MoreScreen

enum class Screen {
    Home, Downloads, History, More
}

data class NavItem(
    val screen: Screen,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.Home) }
    
    val navItems = listOf(
        NavItem(Screen.Home, "Home", Icons.Filled.Home, Icons.Outlined.Home),
        NavItem(Screen.Downloads, "Downloads", Icons.Filled.Download, Icons.Outlined.Download),
        NavItem(Screen.History, "History", Icons.Filled.History, Icons.Outlined.History),
        NavItem(Screen.More, "More", Icons.Filled.MoreHoriz, Icons.Outlined.MoreHoriz)
    )
    
    Row(modifier = Modifier.fillMaxSize()) {
        NavigationRail {
            navItems.forEach { item ->
                NavigationRailItem(
                    icon = {
                        Icon(
                            imageVector = if (currentScreen == item.screen) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentScreen == item.screen,
                    onClick = { currentScreen = item.screen }
                )
            }
        }
        
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (currentScreen) {
                    Screen.Home -> HomeScreen()
                    Screen.Downloads -> DownloadsScreen()
                    Screen.History -> HistoryScreen()
                    Screen.More -> MoreScreen()
                }
            }
        }
    }
}
