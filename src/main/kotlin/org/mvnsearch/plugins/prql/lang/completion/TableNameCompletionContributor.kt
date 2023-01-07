package org.mvnsearch.plugins.prql.lang.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.database.psi.DbPsiFacade
import com.intellij.database.util.DasUtil
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext
import icons.DatabaseIcons
import org.mvnsearch.plugins.prql.lang.PrqlLanguage
import org.mvnsearch.plugins.prql.lang.psi.PrqlTableName
import org.mvnsearch.plugins.prql.lang.psi.PrqlTypes

class TableNameCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC, psiElement(PsiElement::class.java).withElementType(PrqlTypes.IDENTIFIER).withParent(PrqlTableName::class.java).withLanguage(PrqlLanguage),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    val dbPsiFacade = DbPsiFacade.getInstance(parameters.position.project)
                    val dataSources = dbPsiFacade.dataSources
                    if (dataSources.isNotEmpty()) {
                        val dataSource = dataSources[0]
                        val defaultSchema = DasUtil.getSchemas(dataSource).firstOrNull();
                        if (defaultSchema != null) {
                            val tables = DasUtil.getTables(dataSource)
                            for (table in tables) {
                                if (table.dasParent == defaultSchema) {
                                    result.addElement(LookupElementBuilder.create(table.name).withPresentableText(table.name).withIcon(DatabaseIcons.Table))
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}