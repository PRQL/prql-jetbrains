package org.mvnsearch.plugins.prql.lang.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.util.parentOfType
import com.intellij.util.ProcessingContext
import icons.DatabaseIcons
import org.mvnsearch.plugins.prql.lang.PrqlLanguage
import org.mvnsearch.plugins.prql.lang.psi.PrqlPrqlHeaderValue
import org.mvnsearch.plugins.prql.lang.psi.PrqlStmtPrqlDirective
import org.mvnsearch.plugins.prql.lang.psi.PrqlTypes

class DatabaseNameCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC, psiElement(PsiElement::class.java).withElementType(PrqlTypes.IDENTIFIER).withParent(PrqlPrqlHeaderValue::class.java).withLanguage(PrqlLanguage),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    val element = parameters.position
                    val prqlStatement = element.parentOfType<PrqlStmtPrqlDirective>()
                    prqlStatement?.let {
                        if (it.text.contains("sql_dialect:")) {
                            result.addElement(LookupElementBuilder.create("ansi").withIcon(DatabaseIcons.Dbms))
                            result.addElement(LookupElementBuilder.create("bigquery").withIcon(AllIcons.Providers.BigQuery))
                            result.addElement(LookupElementBuilder.create("clickhouse").withIcon(AllIcons.Providers.ClickHouse))
                            result.addElement(LookupElementBuilder.create("generic").withIcon(DatabaseIcons.Dbms))
                            result.addElement(LookupElementBuilder.create("mssql").withIcon(AllIcons.Providers.SqlServer))
                            result.addElement(LookupElementBuilder.create("mysql").withIcon(AllIcons.Providers.Mysql))
                            result.addElement(LookupElementBuilder.create("postgres").withIcon(AllIcons.Providers.Postgresql))
                            result.addElement(LookupElementBuilder.create("sqlite").withIcon(AllIcons.Providers.Sqlite))
                            result.addElement(LookupElementBuilder.create("snowflake").withIcon(AllIcons.Providers.Snowflake))
                        } else if (it.text.contains("target:")) {
                            result.addElement(LookupElementBuilder.create("sql.ansi").withPresentableText("ansi").withIcon(DatabaseIcons.Dbms))
                            result.addElement(LookupElementBuilder.create("sql.any").withPresentableText("any").withIcon(DatabaseIcons.Dbms))
                            result.addElement(LookupElementBuilder.create("sql.bigquery").withPresentableText("bigquery").withIcon(AllIcons.Providers.BigQuery))
                            result.addElement(LookupElementBuilder.create("sql.clickhouse").withPresentableText("clickhouse").withIcon(AllIcons.Providers.ClickHouse))
                            result.addElement(LookupElementBuilder.create("sql.generic").withPresentableText("generic").withIcon(DatabaseIcons.Dbms))
                            result.addElement(LookupElementBuilder.create("sql.mssql").withPresentableText("mssql").withIcon(AllIcons.Providers.SqlServer))
                            result.addElement(LookupElementBuilder.create("sql.mysql").withPresentableText("mysql").withIcon(AllIcons.Providers.Mysql))
                            result.addElement(LookupElementBuilder.create("sql.postgres").withPresentableText("postgres").withIcon(AllIcons.Providers.Postgresql))
                            result.addElement(LookupElementBuilder.create("sql.sqlite").withPresentableText("sqlite").withIcon(AllIcons.Providers.Sqlite))
                            result.addElement(LookupElementBuilder.create("sql.snowflake").withPresentableText("snowflake").withIcon(AllIcons.Providers.Snowflake))
                            result.addElement(LookupElementBuilder.create("sql.duckdb").withPresentableText("duckdb").withIcon(AllIcons.Providers.DuckDB))
                            result.addElement(LookupElementBuilder.create("sql.glaredb").withPresentableText("glaredb").withIcon(AllIcons.Providers.DuckDB))
                        }
                    }
                }
            }
        )
    }
}