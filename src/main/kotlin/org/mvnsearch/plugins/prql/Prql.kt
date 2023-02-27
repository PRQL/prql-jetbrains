package org.mvnsearch.plugins.prql

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

    fun getTarget(dialectId: String): String? {
        return dialectMapping[dialectId]
    }
}