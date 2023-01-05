package org.mvnsearch.plugins.prql.lang.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext
import org.mvnsearch.plugins.prql.lang.PrqlLanguage

class DirectiveCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC, psiElement(PsiElement::class.java).withLanguage(PrqlLanguage),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    val caret = parameters.editor.caretModel.currentCaret
                    val lineOffset = caret.visualLineStart
                    val prefixText = parameters.editor.document.getText(TextRange(lineOffset, caret.offset)).trim()
                    if (!prefixText.contains("#") && !prefixText.contains(' ')) {
                        result.addElement(LookupElementBuilder.create("prql ").withPresentableText("prql"))
                        result.addElement(LookupElementBuilder.create("func ").withPresentableText("func"))
                        result.addElement(LookupElementBuilder.create("table ").withPresentableText("table"))
                        result.addElement(LookupElementBuilder.create("aggregate ").withPresentableText("aggregate"))
                        result.addElement(LookupElementBuilder.create("derive ").withPresentableText("derive"))
                        result.addElement(LookupElementBuilder.create("filter ").withPresentableText("filter"))
                        result.addElement(LookupElementBuilder.create("from ").withPresentableText("from"))
                        result.addElement(LookupElementBuilder.create("group ").withPresentableText("group"))
                        result.addElement(LookupElementBuilder.create("join ").withPresentableText("join"))
                        result.addElement(LookupElementBuilder.create("select ").withPresentableText("select"))
                        result.addElement(LookupElementBuilder.create("sort ").withPresentableText("sort"))
                        result.addElement(LookupElementBuilder.create("take ").withPresentableText("take"))
                        result.addElement(LookupElementBuilder.create("window ").withPresentableText("window"))
                        result.addElement(LookupElementBuilder.create("concat ").withPresentableText("concat"))
                        result.addElement(LookupElementBuilder.create("union ").withPresentableText("union"))
                    }
                }
            }
        )
    }
}