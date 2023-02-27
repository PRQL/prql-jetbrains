package org.mvnsearch.plugins.prql.lang.format

import com.intellij.formatting.service.AsyncDocumentFormattingService
import com.intellij.formatting.service.AsyncFormattingRequest
import com.intellij.formatting.service.FormattingService
import com.intellij.psi.PsiFile
import org.mvnsearch.plugins.prql.lang.psi.PrqlFile
import org.prql.prql4j.PrqlCompiler


class PrqlcExternalFormatter : AsyncDocumentFormattingService() {

    override fun getFeatures(): MutableSet<FormattingService.Feature> {
        return mutableSetOf()
    }

    override fun canFormat(psiFile: PsiFile): Boolean {
        return psiFile is PrqlFile
    }

    override fun createFormattingTask(request: AsyncFormattingRequest): FormattingTask {
        val prqlCode = request.documentText
        return object : FormattingTask {
            override fun run() {
                try {
                    var formattedCode = PrqlCompiler.format(prqlCode).trimEnd()
                    if (prqlCode.endsWith("\n")) {
                        formattedCode += "\n"
                    }
                    request.onTextReady(formattedCode)
                } catch (e: Exception) {
                    request.onError("Failed to format code", e.message ?: "Unknown error")
                }
            }

            override fun cancel(): Boolean {
                return true
            }
        }
    }

    override fun getNotificationGroupId(): String {
        return "prqlc fmt"
    }

    override fun getName(): String {
        return "prqlc fmt"
    }
}