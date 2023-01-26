package org.mvnsearch.plugins.prql.lang.injector

import com.intellij.lang.Language
import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost
import org.mvnsearch.plugins.prql.lang.psi.PrqlStmtFromTextStringArg


class FromTextLanguageInjector : MultiHostInjector {
    private val jsonLanguage: Language = Language.findLanguageByID("JSON")!!
    private val csvLanguage: Language? = Language.findLanguageByID("csv")

    override fun getLanguagesToInject(registrar: MultiHostRegistrar, context: PsiElement) {
        val text = context.text
        if (context is PrqlStmtFromTextStringArg) {
            val quotes = if (text.startsWith('"')) {
                "\"\"\""
            } else {
                "'''"
            }
            var offset = text.indexOf(quotes) + 3
            if (offset > 0) {
                if (text[offset] == '\n') {
                    offset += 1
                }
                val endOffset = text.indexOf(quotes, offset)
                if (endOffset > offset) {
                    //detect language
                    val formatText = context.parent.children[0].text
                    val language = if (formatText.contains(":csv")) {
                        csvLanguage
                    } else if (formatText.contains(":json")) {
                        jsonLanguage
                    } else {
                        null
                    }
                    if (language != null) {
                        val injectionTextRange = TextRange(offset, context.textLength - 3)
                        registrar.startInjecting(language)
                        registrar.addPlace(null, null, context as PsiLanguageInjectionHost, injectionTextRange)
                        registrar.doneInjecting()
                    }
                }
            }
        }
    }

    override fun elementsToInjectIn(): MutableList<out Class<out PsiElement>> {
        return mutableListOf(PrqlStmtFromTextStringArg::class.java)
    }

}