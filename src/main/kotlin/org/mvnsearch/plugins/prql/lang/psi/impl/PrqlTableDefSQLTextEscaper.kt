package org.mvnsearch.plugins.prql.lang.psi.impl

import com.intellij.openapi.util.TextRange
import com.intellij.psi.LiteralTextEscaper
import org.mvnsearch.plugins.prql.lang.psi.PrqlStmtTableFromSql

class PrqlTableDefSQLTextEscaper(private val sqlCodeBlock: PrqlStmtTableFromSql) : LiteralTextEscaper<PrqlStmtTableFromSql>(sqlCodeBlock) {

    override fun isOneLine(): Boolean {
        return sqlCodeBlock.text.lastIndexOf('\n') == 0
    }

    override fun getOffsetInHost(offsetInDecoded: Int, rangeInsideHost: TextRange): Int {
        return rangeInsideHost.startOffset + offsetInDecoded
    }

    override fun decode(rangeInsideHost: TextRange, outChars: StringBuilder): Boolean {
        return try {
            outChars.append(rangeInsideHost.substring(sqlCodeBlock.text))
            true
        } catch (e: Throwable) {
            false
        }
    }
}