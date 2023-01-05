package org.mvnsearch.plugins.prql.lang.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.LiteralTextEscaper
import com.intellij.psi.PsiLanguageInjectionHost
import org.mvnsearch.plugins.prql.lang.psi.PrqlElementFactory
import org.mvnsearch.plugins.prql.lang.psi.PrqlTableFromSql

abstract class PrqlTableFromSQLInjectionHost(node: ASTNode) : ASTWrapperPsiElement(node), PrqlTableFromSql {

    override fun createLiteralTextEscaper(): LiteralTextEscaper<out PsiLanguageInjectionHost> {
        return PrqlTableFromSQLTextEscaper(this)
    }

    override fun updateText(text: String): PrqlTableFromSql {
        val sqlSString = PrqlElementFactory.createTableFromSQL(project, text)
        return this.replace(sqlSString) as PrqlTableFromSql
    }

    override fun isValidHost(): Boolean {
        return true
    }
}