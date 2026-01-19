package com.deniscerri.ytdl.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

/**
 * PLATFORM-SPECIFIC: SQLDelight driver factory for desktop
 */
class DriverFactory {
    fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        YTDLnisDatabase.Schema.create(driver)
        return driver
    }
}
