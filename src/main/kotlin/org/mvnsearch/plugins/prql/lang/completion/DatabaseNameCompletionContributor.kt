package org.mvnsearch.plugins.prql.lang.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.util.parentOfType
import com.intellij.util.ProcessingContext
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
                            result.addElement(LookupElementBuilder.create("ansi"))
                            result.addElement(LookupElementBuilder.create("bigquery"))
                            result.addElement(LookupElementBuilder.create("clickhouse"))
                            result.addElement(LookupElementBuilder.create("generic"))
                            result.addElement(LookupElementBuilder.create("hive"))
                            result.addElement(LookupElementBuilder.create("mssql"))
                            result.addElement(LookupElementBuilder.create("mysql"))
                            result.addElement(LookupElementBuilder.create("postgres"))
                            result.addElement(LookupElementBuilder.create("sqlite"))
                            result.addElement(LookupElementBuilder.create("snowflake"))
                        } else if (it.text.contains("target:")) {
                            result.addElement(LookupElementBuilder.create("sql.ansi").withPresentableText("ansi"))
                            result.addElement(LookupElementBuilder.create("sql.bigquery").withPresentableText("bigquery"))
                            result.addElement(LookupElementBuilder.create("sql.clickhouse").withPresentableText("clickhouse"))
                            result.addElement(LookupElementBuilder.create("sql.generic").withPresentableText("generic"))
                            result.addElement(LookupElementBuilder.create("sql.hive").withPresentableText("hive"))
                            result.addElement(LookupElementBuilder.create("sql.mssql").withPresentableText("mssql"))
                            result.addElement(LookupElementBuilder.create("sql.mysql").withPresentableText("mysql"))
                            result.addElement(LookupElementBuilder.create("sql.postgres").withPresentableText("postgres"))
                            result.addElement(LookupElementBuilder.create("sql.sqlite").withPresentableText("sqlite"))
                            result.addElement(LookupElementBuilder.create("sql.snowflake").withPresentableText("snowflake"))
                        }
                    }
                }
            }
        )
    }
}