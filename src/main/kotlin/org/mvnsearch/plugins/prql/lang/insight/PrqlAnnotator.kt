package org.mvnsearch.plugins.prql.lang.insight

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import com.intellij.sql.editor.SqlColors
import org.mvnsearch.plugins.prql.lang.psi.PrqlTypes


/**
 * use SqlColors for high light
 * @author linux_china
 */
class PrqlAnnotator : Annotator {
    companion object {
        val STANDARD_FUNCTION_NAMES =
            arrayOf("min", "max", "sum", "avg", "stddev", "average", "count", "count_distinct", "lag", "lead", "first", "last", "rank", "rank_dense", "row_number", "round")
    }

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        when (element.elementType) {

            PrqlTypes.PARAM2 -> {
                holder.newSilentAnnotation(HighlightSeverity.WEAK_WARNING)
                    .range(element.textRange)
                    .textAttributes(SqlColors.SQL_PARAMETER)
                    .tooltip("Not supported officially, only for JetBrains plugin")
                    .create()
            }

            PrqlTypes.F_STRING -> {
                highLightFString(element, holder)
            }

            PrqlTypes.S_STRING,
            PrqlTypes.S_INDENTED_STRING -> {
                highLightSString(element, holder)
            }

            PrqlTypes.FUNC_CALL -> {  // high light function name
                highLightFunctionName(element, holder)
            }
        }
    }


    private fun highLightFunctionName(element: PsiElement, holder: AnnotationHolder) {
        val rangeOffset = element.textRange.startOffset
        val text = element.text
        val offset = text.indexOf(" ")
        if (offset > 0) {
            var firstWord = text.substring(0, offset)
            var startOffset = 0
            if (firstWord.startsWith("(")) {
                firstWord = firstWord.substring(1);
                startOffset = 1
            }
            if (STANDARD_FUNCTION_NAMES.contains(firstWord)) {
                val range = TextRange(rangeOffset + startOffset, rangeOffset + offset)
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(range).textAttributes(SqlColors.SQL_PROCEDURE).create()
            }
        }
    }

    private fun highLightSString(element: PsiElement, holder: AnnotationHolder) {
        val rangeOffset = element.textRange.startOffset
        val text = element.text
        val separator = if (text.contains("\"\"\"")) {
            "\"\"\""
        } else {
            "\""
        }
        val quotaOffset = text.indexOf(separator)
        val quotEndOffset = text.lastIndexOf(separator) + separator.length
        if (quotEndOffset > quotaOffset) {
            val range = TextRange(rangeOffset + quotaOffset, rangeOffset + quotEndOffset)
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(range).textAttributes(SqlColors.SQL_STRING).create()
        }
        // high light column name
        var offset = getLeftBraceInSString(text, 0)
        while (offset > 0) {
            val endOffset = text.indexOf("}", offset + 1)
            offset = if (endOffset > offset) {
                // high light column name
                val columnNameRange = TextRange(rangeOffset + offset + 1, rangeOffset + endOffset)
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(columnNameRange).textAttributes(SqlColors.SQL_COLUMN).create()
                // high light brace { }
                val lbraceRange = TextRange(rangeOffset + offset, rangeOffset + offset + 1)
                val rbraceRange = TextRange(rangeOffset + endOffset, rangeOffset + endOffset + 1)
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(lbraceRange).textAttributes(SqlColors.SQL_BRACES).create()
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(rbraceRange).textAttributes(SqlColors.SQL_BRACES).create()
                getLeftBraceInSString(text, endOffset + 1)
            } else {
                -1
            }
        }
    }

    private fun getLeftBraceInSString(text: String, offset: Int): Int {
        val textLength = text.length
        var leftBraceOffset = text.indexOf("{", offset)
        if (leftBraceOffset > 0 && leftBraceOffset < textLength - 1) {
            if (text[leftBraceOffset + 1] == '{') {
                leftBraceOffset += 1
            }
        }
        return leftBraceOffset
    }

    private fun highLightFString(element: PsiElement, holder: AnnotationHolder) {
        val rangeOffset = element.textRange.startOffset
        val text = element.text
        //high light as string
        val quotaOffset = text.indexOf("\"")
        val quotEndOffset = text.lastIndexOf("\"") + 1
        if (quotEndOffset > quotaOffset) {
            val range = TextRange(rangeOffset + quotaOffset, rangeOffset + quotEndOffset)
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(range).textAttributes(SqlColors.SQL_STRING).create()
        }
        // high light column name
        var offset = text.indexOf("{")
        while (offset > 0) {
            val endOffset = text.indexOf("}", offset + 1)
            offset = if (endOffset > offset) {
                // high light column name
                val columnNameRange = TextRange(rangeOffset + offset + 1, rangeOffset + endOffset)
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(columnNameRange).textAttributes(SqlColors.SQL_COLUMN).create()
                // high light brace { }
                val lbraceRange = TextRange(rangeOffset + offset, rangeOffset + offset + 1)
                val rbraceRange = TextRange(rangeOffset + endOffset, rangeOffset + endOffset + 1)
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(lbraceRange).textAttributes(SqlColors.SQL_BRACES).create()
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(rbraceRange).textAttributes(SqlColors.SQL_BRACES).create()
                text.indexOf("{", endOffset + 1)
            } else {
                -1
            }
        }
    }


}