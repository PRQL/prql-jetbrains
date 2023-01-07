package org.mvnsearch.plugins.prql.lang.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext
import org.mvnsearch.plugins.prql.ide.icons.PrqlIcons
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
                    val prefixText = parameters.editor.document.getText(TextRange(lineOffset, caret.offset)).trimStart()
                    if (!prefixText.contains("#") && !prefixText.contains(' ')) {
                        result.addElement(LookupElementBuilder.create("prql ").withPresentableText("prql").withIcon(PrqlIcons.PRQL_FILE))
                        result.addElement(LookupElementBuilder.create("func ").withPresentableText("func").withIcon(PrqlIcons.PRQL_FILE))
                        result.addElement(LookupElementBuilder.create("table ").withPresentableText("table").withIcon(PrqlIcons.PRQL_FILE))
                        result.addElement(LookupElementBuilder.create("aggregate ").withPresentableText("aggregate").withIcon(PrqlIcons.PRQL_FILE))
                        result.addElement(LookupElementBuilder.create("derive ").withPresentableText("derive").withIcon(PrqlIcons.PRQL_FILE))
                        result.addElement(LookupElementBuilder.create("filter ").withPresentableText("filter").withIcon(PrqlIcons.PRQL_FILE))
                        result.addElement(LookupElementBuilder.create("from ").withPresentableText("from").withIcon(PrqlIcons.PRQL_FILE))
                        result.addElement(LookupElementBuilder.create("group ").withPresentableText("group").withIcon(PrqlIcons.PRQL_FILE))
                        result.addElement(LookupElementBuilder.create("join ").withPresentableText("join").withIcon(PrqlIcons.PRQL_FILE))
                        result.addElement(LookupElementBuilder.create("select ").withPresentableText("select").withIcon(PrqlIcons.PRQL_FILE))
                        result.addElement(LookupElementBuilder.create("sort ").withPresentableText("sort").withIcon(PrqlIcons.PRQL_FILE))
                        result.addElement(LookupElementBuilder.create("take ").withPresentableText("take").withIcon(PrqlIcons.PRQL_FILE))
                        result.addElement(LookupElementBuilder.create("window ").withPresentableText("window").withIcon(PrqlIcons.PRQL_FILE))
                        result.addElement(LookupElementBuilder.create("concat ").withPresentableText("concat").withIcon(PrqlIcons.PRQL_FILE))
                        result.addElement(LookupElementBuilder.create("union ").withPresentableText("union").withIcon(PrqlIcons.PRQL_FILE))
                    }
                }
            }
        )
    }
}