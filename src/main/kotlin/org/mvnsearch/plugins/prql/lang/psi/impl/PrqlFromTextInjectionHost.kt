package org.mvnsearch.plugins.prql.lang.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.LiteralTextEscaper
import com.intellij.psi.PsiLanguageInjectionHost
import org.mvnsearch.plugins.prql.lang.psi.PrqlElementFactory
import org.mvnsearch.plugins.prql.lang.psi.PrqlStmtFromTextStringArg

abstract class PrqlFromTextInjectionHost(node: ASTNode) : ASTWrapperPsiElement(node), PrqlStmtFromTextStringArg {

    override fun createLiteralTextEscaper(): LiteralTextEscaper<out PsiLanguageInjectionHost> {
        return PrqlFromTextTextEscaper(this)
    }

    override fun updateText(text: String): PrqlStmtFromTextStringArg {
        var format = "csv"
        if (text.length > 6) {
            val data = text.substring(3, text.length - 3).trim()
            if (data.startsWith('[') || data.startsWith('{')) {
                format = "json"
            }
        }
        val dataBlock = PrqlElementFactory.createTableFromText(project, format, text)
        return this.replace(dataBlock) as PrqlStmtFromTextStringArg
    }

    override fun isValidHost(): Boolean {
        return true
    }
}