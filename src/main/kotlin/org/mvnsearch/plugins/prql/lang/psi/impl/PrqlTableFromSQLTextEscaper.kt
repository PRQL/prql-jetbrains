package org.mvnsearch.plugins.prql.lang.psi.impl

import com.intellij.openapi.util.TextRange
import com.intellij.psi.LiteralTextEscaper
import org.mvnsearch.plugins.prql.lang.psi.PrqlStmtTableFromSql
import org.mvnsearch.plugins.prql.lang.psi.PrqlTableFromSql

class PrqlTableFromSQLTextEscaper(private val sqlSString: PrqlTableFromSql) : LiteralTextEscaper<PrqlTableFromSql>(sqlSString) {

    override fun isOneLine(): Boolean {
        return true;
    }

    override fun getOffsetInHost(offsetInDecoded: Int, rangeInsideHost: TextRange): Int {
        return rangeInsideHost.startOffset + offsetInDecoded
    }

    override fun decode(rangeInsideHost: TextRange, outChars: StringBuilder): Boolean {
        return try {
            outChars.append(rangeInsideHost.substring(sqlSString.text))
            true
        } catch (e: Throwable) {
            false
        }
    }
}