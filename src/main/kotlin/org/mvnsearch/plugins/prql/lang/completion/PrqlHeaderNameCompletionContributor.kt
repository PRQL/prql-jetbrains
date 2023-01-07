package org.mvnsearch.plugins.prql.lang.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext
import org.mvnsearch.plugins.prql.lang.PrqlLanguage
import org.mvnsearch.plugins.prql.lang.psi.PrqlPrqlHeaderName
import org.mvnsearch.plugins.prql.lang.psi.PrqlTypes

class PrqlHeaderNameCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC, psiElement(PsiElement::class.java).withElementType(PrqlTypes.IDENTIFIER).withParent(PrqlPrqlHeaderName::class.java).withLanguage(PrqlLanguage),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    result.addElement(LookupElementBuilder.create("target:").withPresentableText("target").withIcon(icons.DatabaseIcons.Extension))
                    result.addElement(LookupElementBuilder.create("version:").withPresentableText("version").withIcon(icons.DatabaseIcons.Extension))
                }
            }
        )
    }
}