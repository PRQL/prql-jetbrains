package org.mvnsearch.plugins.prql

import com.intellij.openapi.util.SystemInfo
import java.io.File

object Prql {
    private val dialectMapping = mapOf(
        "PostgreSQL" to "postgres",
        "MySQL" to "postgres",
        "MariaDB" to "postgres",
        "GenericSQL" to "generic",
        "BigQuery" to "bigquery",
        "ClickHouse" to "clickhouse",
        "HiveQL" to "hive",
        "TSQL" to "mssql",
        "SQLite" to "sqlite",
        "Snowflake" to "snowflake",
    )

    fun getPrqlCompilerCmdAbsolutionPath(): String {
        val userHome = System.getProperty("user.home")
        if (File(userHome, ".cargo/bin/prqlc").exists()) {
            return File(userHome, ".cargo/bin/prqlc").absolutePath
        }
        return if (SystemInfo.isWindows) {
            return "prqlc"
        } else {
            if (File("/usr/local/bin/prqlc").exists()) {
                "/usr/local/bin/prqlc"
            } else {
                "prqlc"
            }
        }
    }

    fun getTarget(dialectId: String): String? {
        return dialectMapping[dialectId]
    }
}