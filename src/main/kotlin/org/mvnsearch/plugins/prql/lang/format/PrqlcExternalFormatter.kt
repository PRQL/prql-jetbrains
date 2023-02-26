package org.mvnsearch.plugins.prql.lang.format

import com.intellij.formatting.service.AsyncDocumentFormattingService
import com.intellij.formatting.service.AsyncFormattingRequest
import com.intellij.formatting.service.FormattingService
import com.intellij.psi.PsiFile
import org.mvnsearch.plugins.prql.Prql
import org.mvnsearch.plugins.prql.lang.psi.PrqlFile
import java.io.File


class PrqlcExternalFormatter : AsyncDocumentFormattingService() {

    override fun getFeatures(): MutableSet<FormattingService.Feature> {
        return mutableSetOf()
    }

    override fun canFormat(psiFile: PsiFile): Boolean {
        if (psiFile is PrqlFile) {
            return File(Prql.getPrqlCompilerCmdAbsolutionPath()).exists()
        }
        return false
    }

    override fun createFormattingTask(request: AsyncFormattingRequest): FormattingTask {
        val prqlCode = request.documentText
        return object : FormattingTask {
            override fun run() {
                val process = ProcessBuilder().command(Prql.getPrqlCompilerCmdAbsolutionPath(), "fmt").start();
                process.outputStream.use {
                    it.write(prqlCode.toByteArray())
                    it.flush()
                }
                process.waitFor()
                if (process.exitValue() == 0) {
                    var formattedCode = process.inputStream.bufferedReader().readText().trimEnd()
                    if (prqlCode.endsWith("\n")) {
                        formattedCode += "\n"
                    }
                    request.onTextReady(formattedCode)
                } else {
                    request.onError("Failed to format code", process.errorStream.bufferedReader().readText())
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