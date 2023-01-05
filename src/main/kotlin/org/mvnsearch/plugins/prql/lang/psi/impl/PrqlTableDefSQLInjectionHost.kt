package org.mvnsearch.plugins.prql.lang.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.LiteralTextEscaper
import com.intellij.psi.PsiLanguageInjectionHost
import org.mvnsearch.plugins.prql.lang.psi.PrqlElementFactory
import org.mvnsearch.plugins.prql.lang.psi.PrqlStmtTableFromSql

abstract class PrqlTableDefSQLInjectionHost(node: ASTNode) : ASTWrapperPsiElement(node), PrqlStmtTableFromSql {

    override fun createLiteralTextEscaper(): LiteralTextEscaper<out PsiLanguageInjectionHost> {
        return PrqlTableDefSQLTextEscaper(this)
    }

    override fun updateText(text: String): PrqlStmtTableFromSql {
        val codeBlock = PrqlElementFactory.createTableDefSQLCodeBlock(project, text)
        return this.replace(codeBlock) as PrqlStmtTableFromSql
    }

    override fun isValidHost(): Boolean {
        return true
    }
}