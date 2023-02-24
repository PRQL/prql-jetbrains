package org.mvnsearch.plugins.prql.lang.insight

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import org.mvnsearch.plugins.prql.lang.psi.PrqlTypes


class PrqlBraceMatcher : PairedBraceMatcher {
    private val pairs = arrayOf(
        BracePair(PrqlTypes.LBRACK, PrqlTypes.RBRACK, true),
        BracePair(PrqlTypes.LPAREN, PrqlTypes.RPAREN, true)
    )

    override fun getPairs(): Array<BracePair> {
        return pairs
    }

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean {
        return true
    }

    override fun getCodeConstructStart(file: PsiFile?, openingBraceOffset: Int): Int {
        return openingBraceOffset
    }
}