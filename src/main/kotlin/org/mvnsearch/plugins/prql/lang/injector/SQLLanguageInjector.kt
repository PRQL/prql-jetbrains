package org.mvnsearch.plugins.prql.lang.injector

import com.intellij.lang.Language
import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost
import org.mvnsearch.plugins.prql.lang.psi.PrqlStmtTableFromSql
import org.mvnsearch.plugins.prql.lang.psi.PrqlTableFromSql


class SQLLanguageInjector : MultiHostInjector, DumbAware {
    private val sqlLanguage: Language = Language.findLanguageByID("SQL")!!


    override fun getLanguagesToInject(registrar: MultiHostRegistrar, context: PsiElement) {
        val text = context.text
        if (context is PrqlStmtTableFromSql) {
            var offset = text.indexOf("\"\"\"") + 3
            if (offset > 0) {
                if (text[offset] == '\n') {
                    offset += 1
                }
                val endOffset = text.indexOf("\"\"\"", offset)
                if (endOffset > offset) {
                    val injectionTextRange = TextRange(offset, context.textLength - 3)
                    registrar.startInjecting(sqlLanguage)
                    registrar.addPlace(null, null, context as PsiLanguageInjectionHost, injectionTextRange)
                    registrar.doneInjecting()
                }
            }
        } else if (context is PrqlTableFromSql) {
            val offset = 2
            val endOffset = text.lastIndexOf('"')
            if (endOffset > offset) {
                val injectionTextRange = TextRange(offset, endOffset)
                registrar.startInjecting(sqlLanguage)
                registrar.addPlace(null, null, context as PsiLanguageInjectionHost, injectionTextRange)
                registrar.doneInjecting()
            }
        }
    }

    override fun elementsToInjectIn(): MutableList<out Class<out PsiElement>> {
        return mutableListOf(PrqlStmtTableFromSql::class.java, PrqlTableFromSql::class.java)
    }

}