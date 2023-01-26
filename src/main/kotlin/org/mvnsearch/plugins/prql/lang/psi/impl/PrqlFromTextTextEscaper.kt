package org.mvnsearch.plugins.prql.lang.psi.impl

import com.intellij.openapi.util.TextRange
import com.intellij.psi.LiteralTextEscaper
import org.mvnsearch.plugins.prql.lang.psi.PrqlStmtFromTextStringArg

class PrqlFromTextTextEscaper(private val dataBlock: PrqlStmtFromTextStringArg) : LiteralTextEscaper<PrqlStmtFromTextStringArg>(dataBlock) {

    override fun isOneLine(): Boolean {
        return dataBlock.text.lastIndexOf('\n') == 0
    }

    override fun getOffsetInHost(offsetInDecoded: Int, rangeInsideHost: TextRange): Int {
        return rangeInsideHost.startOffset + offsetInDecoded
    }

    override fun decode(rangeInsideHost: TextRange, outChars: StringBuilder): Boolean {
        return try {
            outChars.append(rangeInsideHost.substring(dataBlock.text))
            true
        } catch (e: Throwable) {
            false
        }
    }
}