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
            PrqlTypes.RESERVED_KEYWORD -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange)
                    .textAttributes(SqlColors.SQL_KEYWORD).create()
            }

            PrqlTypes.FUNC_NAME -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange)
                    .textAttributes(SqlColors.SQL_PROCEDURE).create()
            }

            PrqlTypes.RAW_LITERAL,
            PrqlTypes.COLUMN_NAME -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange)
                    .textAttributes(SqlColors.SQL_COLUMN).create()
            }

            PrqlTypes.TABLE_NAME -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange)
                    .textAttributes(SqlColors.SQL_TABLE).create()
            }

            PrqlTypes.COMMENT -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange).textAttributes(SqlColors.SQL_COMMENT).create()
            }

            PrqlTypes.STRING_LITERAL,
            PrqlTypes.CHAR_LITERAL,
            PrqlTypes.INDENTED_STRING -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange)
                    .textAttributes(SqlColors.SQL_STRING).create()
            }

            PrqlTypes.DOUBLE_LITERAL,
            PrqlTypes.INTEGER_LITERAL -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange)
                    .textAttributes(SqlColors.SQL_NUMBER).create()
            }

            PrqlTypes.COMMA -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange)
                    .textAttributes(SqlColors.SQL_COMMA).create()
            }

            PrqlTypes.LBRACE,
            PrqlTypes.RBRACE -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange)
                    .textAttributes(SqlColors.SQL_BRACES).create()
            }

            PrqlTypes.LBRACK,
            PrqlTypes.RBRACK -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange)
                    .textAttributes(SqlColors.SQL_BRACKETS).create()
            }

            PrqlTypes.LPAREN,
            PrqlTypes.RPAREN -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange)
                    .textAttributes(SqlColors.SQL_PARENS).create()
            }

            PrqlTypes.DOT -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange)
                    .textAttributes(SqlColors.SQL_DOT).create()
            }

            PrqlTypes.STMT_FUNC_DEF_PARAM -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange)
                    .textAttributes(SqlColors.SQL_PARAMETER).create()
            }

            PrqlTypes.PARAM -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange)
                    .textAttributes(SqlColors.SQL_VARIABLE).create()
            }

            PrqlTypes.DATE_LITERAL,
            PrqlTypes.TIME_LITERAL,
            PrqlTypes.TIMESTAMP_LITERAL,
            PrqlTypes.INTERVAL_LITERAL -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange)
                    .textAttributes(SqlColors.SQL_SYNTHETIC_ENTITY).create()
            }

            // literals support by PRQL
            PrqlTypes.NULL,
            PrqlTypes.BOOL_FALSE,
            PrqlTypes.BOOL_TRUE -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange)
                    .textAttributes(SqlColors.SQL_SYNTHETIC_ENTITY).create()
            }

            PrqlTypes.F_STRING -> {
                highLightColumnsInFString(element, holder)
            }

            PrqlTypes.AGGREGATE_FUNCTION -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(element.textRange)
                    .textAttributes(SqlColors.SQL_PROCEDURE).create()
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

    private fun highLightColumnsInFString(element: PsiElement, holder: AnnotationHolder) {
        val rangeOffset = element.textRange.startOffset
        val text = element.text
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