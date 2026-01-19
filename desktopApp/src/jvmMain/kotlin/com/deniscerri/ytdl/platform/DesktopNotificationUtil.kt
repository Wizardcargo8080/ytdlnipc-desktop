package com.deniscerri.ytdl.platform

import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon
import java.awt.TrayIcon.MessageType

/**
 * PLATFORM-SPECIFIC: Desktop notification system
 * Replaces Android NotificationUtil with Java AWT System Tray notifications
 */
object DesktopNotificationUtil {
    
    private var trayIcon: TrayIcon? = null
    private var isInitialized = false
    
    /**
     * Initialize the system tray icon for notifications
     */
    fun initialize(appName: String = "YTDLnis") {
        if (isInitialized) return
        
        if (!SystemTray.isSupported()) {
            println("System tray not supported on this platform")
            return
        }
        
        try {
            val systemTray = SystemTray.getSystemTray()
            val image = Toolkit.getDefaultToolkit().createImage(
                DesktopNotificationUtil::class.java.getResource("/icons/ic_launcher.png")
            ) ?: Toolkit.getDefaultToolkit().createImage(ByteArray(0))
            
            trayIcon = TrayIcon(image, appName).apply {
                isImageAutoSize = true
            }
            
            systemTray.add(trayIcon)
            isInitialized = true
        } catch (e: Exception) {
            println("Failed to initialize system tray: ${e.message}")
        }
    }
    
    /**
     * Show a download started notification
     * Matches Android: notifyDownloadStart(...)
     */
    fun notifyDownloadStart(title: String) {
        showNotification(
            title = "Download Started",
            message = title,
            type = MessageType.INFO
        )
    }
    
    /**
     * Show a download completed notification
     * Matches Android: notifyDownloadFinished(...)
     */
    fun notifyDownloadFinished(title: String, filePath: String) {
        showNotification(
            title = "Download Finished",
            message = "$title\n$filePath",
            type = MessageType.INFO
        )
    }
    
    /**
     * Show a download error notification
     * Matches Android: notifyDownloadError(...)
     */
    fun notifyDownloadError(title: String, error: String) {
        showNotification(
            title = "Download Error",
            message = "$title\n$error",
            type = MessageType.ERROR
        )
    }
    
    /**
     * Show a download cancelled notification
     * Matches Android: notifyDownloadCancelled(...)
     */
    fun notifyDownloadCancelled(title: String) {
        showNotification(
            title = "Download Cancelled",
            message = title,
            type = MessageType.WARNING
        )
    }
    
    /**
     * Show a download progress update (for active downloads)
     * Matches Android: updateDownloadNotification(...)
     */
    fun updateDownloadProgress(downloadId: Long, title: String, progress: Int, speed: String) {
        // Desktop doesn't support progress notifications the same way
        // We could show periodic updates, but for now we just log
        // In a full implementation we'd use a custom notification window
        println("Download $downloadId: $title - $progress% @ $speed")
    }
    
    /**
     * Show a scheduled download reminder
     * Matches Android: notifyScheduledDownload(...)
     */
    fun notifyScheduledDownload(title: String, scheduledTime: String) {
        showNotification(
            title = "Scheduled Download",
            message = "$title\nScheduled for: $scheduledTime",
            type = MessageType.INFO
        )
    }
    
    /**
     * Show an update available notification
     * Matches Android: notifyUpdateAvailable(...)
     */
    fun notifyUpdateAvailable(version: String) {
        showNotification(
            title = "Update Available",
            message = "YTDLnis $version is available",
            type = MessageType.INFO
        )
    }
    
    /**
     * Cancel all notifications
     * Matches Android: cancelAllNotifications()
     */
    fun cancelAllNotifications() {
        // AWT TrayIcon doesn't support canceling displayed messages
        // They auto-dismiss after a short time
    }
    
    /**
     * Cancel a specific notification by ID
     * Matches Android: cancelNotification(id)
     */
    fun cancelNotification(notificationId: Int) {
        // AWT TrayIcon doesn't support this
    }
    
    /**
     * Generic notification method
     */
    private fun showNotification(title: String, message: String, type: MessageType = MessageType.NONE) {
        if (!isInitialized) {
            initialize()
        }
        
        trayIcon?.displayMessage(title, message, type)
    }
    
    /**
     * Cleanup on app exit
     */
    fun cleanup() {
        if (isInitialized && trayIcon != null) {
            try {
                SystemTray.getSystemTray().remove(trayIcon)
            } catch (e: Exception) {
                // Ignore
            }
        }
        isInitialized = false
        trayIcon = null
    }
}
