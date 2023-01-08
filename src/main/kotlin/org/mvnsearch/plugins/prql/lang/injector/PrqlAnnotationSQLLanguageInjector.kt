package org.mvnsearch.plugins.prql.lang.injector

import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost
import org.mvnsearch.plugins.prql.lang.PrqlLanguage


class PrqlAnnotationSQLLanguageInjector : MultiHostInjector, DumbAware {


    override fun getLanguagesToInject(registrar: MultiHostRegistrar, context: PsiElement) {
        if (context is PsiAnnotation) {
            val qualifiedName = context.qualifiedName
            if (qualifiedName!=null && qualifiedName.endsWith(".PRQL")) {
                context.parameterList.attributes.forEach { attribute ->
                    val name = attribute.name
                    val prqlCodeBlock = attribute.value
                    if ((name == null || name == "value") && prqlCodeBlock != null) {
                        val prqlCode = prqlCodeBlock.text
                        var offset = prqlCode.indexOf("\"\"\"")
                        if (offset >= 0) {  // code block style
                            offset += 3
                            if (prqlCode[offset] == '\n') {
                                offset += 1
                            }
                            val endOffset = prqlCode.lastIndexOf("\"\"\"")
                            if (endOffset > offset) {
                                val trimmedText = prqlCode.subSequence(0, endOffset).trimEnd()
                                val adjustedOffset = prqlCode.length - trimmedText.length
                                val injectionTextRange = TextRange(offset, prqlCodeBlock.textLength - adjustedOffset)
                                registrar.startInjecting(PrqlLanguage)
                                registrar.addPlace(null, null, prqlCodeBlock as PsiLanguageInjectionHost, injectionTextRange)
                                registrar.doneInjecting()
                            }
                        } else if (prqlCode.indexOf('"') >= 0) { //one-line style
                            offset = prqlCode.indexOf('"') + 1
                            if (offset > 0) {
                                val endOffset = prqlCode.lastIndexOf('"')
                                if (endOffset > offset) {
                                    val injectionTextRange = TextRange(offset, endOffset)
                                    registrar.startInjecting(PrqlLanguage)
                                    registrar.addPlace(null, null, prqlCodeBlock as PsiLanguageInjectionHost, injectionTextRange)
                                    registrar.doneInjecting()
                                }
                            }
                        }
                    }
                }

            }

        }
    }

    override fun elementsToInjectIn(): MutableList<out Class<out PsiElement>> {
        return mutableListOf(PsiAnnotation::class.java)
    }

}