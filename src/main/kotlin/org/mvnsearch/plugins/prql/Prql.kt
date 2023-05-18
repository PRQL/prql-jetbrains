package org.mvnsearch.plugins.prql

import com.intellij.openapi.project.Project
import com.intellij.sql.dialects.SqlDialectMappings
import org.prql.prql4j.PrqlCompiler

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

    @Throws(Exception::class)
    fun transformPrql(dialect: String?, prqlCode: String, format: Boolean, project: Project): String {
        val prqlTarget = getPrqlDatabaseTarget(dialect, project)
        var cleanPrql = prqlCode
        // convert $id to s"$id"
        if (cleanPrql.contains(" \$")) {
            cleanPrql = cleanPrql.replace(" \\$([\\w.]+)".toRegex(), " s\"\\\$\$1\"")
        }
        // convert :id to s":id"
        if (cleanPrql.contains(" :")) {
            cleanPrql = cleanPrql.replace(" :([\\w.]+)".toRegex(), " s\":\$1\"")
        }
        // replace ` ?` to ` $0` for jdbc
        if (cleanPrql.contains(" ?")) {
            cleanPrql = cleanPrql.replace(" ?", "$0");
        }
        var sql = PrqlCompiler.toSql(cleanPrql, "sql.${prqlTarget}", format, false)
        // convert $0 to ?
        if (sql.contains(" $0")) {
            sql = sql.replace(" $0", " ?")
        }
        // clean ` $ ` to ` $`
        if (sql.contains(" $ ")) {
            sql = sql.replace(" $ ", " $")
        }
        return sql
    }

    private fun getPrqlDatabaseTarget(dialect: String?, project: Project): String {
        return if (dialect != null) {
            dialectMapping[dialect]
        } else {
            val defaultDialect = SqlDialectMappings.getMapping(project, null)
            dialectMapping[defaultDialect.id]
        } ?: "generic"
    }
}