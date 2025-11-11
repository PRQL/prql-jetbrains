package org.mvnsearch.plugins.prql.lang.lighter

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import com.intellij.sql.editor.SqlColors
import org.mvnsearch.plugins.prql.lang.lexer.PrqlLexerAdapter
import org.mvnsearch.plugins.prql.lang.psi.PrqlTypes

class PrqlSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer {
        return PrqlLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        val textAttributesKey = when (tokenType) {
            PrqlTypes.RESERVED_KEYWORD,
            PrqlTypes.THIS_KEYWORD,
            PrqlTypes.THAT_KEYWORD,
            PrqlTypes.LET,
            PrqlTypes.SWITCH,
            PrqlTypes.CASE,
            PrqlTypes.LOOP,
            -> SqlColors.SQL_KEYWORD

            PrqlTypes.FUNC_NAME -> SqlColors.SQL_PROCEDURE
            PrqlTypes.RAW_LITERAL, PrqlTypes.COLUMN_NAME -> SqlColors.SQL_COLUMN
            PrqlTypes.TABLE_NAME -> SqlColors.SQL_TABLE
            PrqlTypes.COMMENT -> SqlColors.SQL_COMMENT
            PrqlTypes.STRING_LITERAL,
            PrqlTypes.CHAR_LITERAL,
            PrqlTypes.INDENTED_STRING -> SqlColors.SQL_STRING

            PrqlTypes.DOUBLE_LITERAL,
            PrqlTypes.INTEGER_LITERAL,
            PrqlTypes.BINARY_NUMERICAL,
            PrqlTypes.OCTAL_NUMERICAL,
            PrqlTypes.HEXADECIMAL_NUMERICAL,
            -> SqlColors.SQL_NUMBER

            PrqlTypes.COMMA -> SqlColors.SQL_COMMA
            PrqlTypes.LBRACE, PrqlTypes.RBRACE -> SqlColors.SQL_BRACES
            PrqlTypes.LBRACK, PrqlTypes.RBRACK -> SqlColors.SQL_BRACKETS
            PrqlTypes.LPAREN, PrqlTypes.RPAREN -> SqlColors.SQL_PARENS
            PrqlTypes.DOT -> SqlColors.SQL_DOT
            PrqlTypes.FUNCTION_PARAM -> SqlColors.SQL_PARAMETER
            PrqlTypes.PARAM -> SqlColors.SQL_VARIABLE
            PrqlTypes.DATE_LITERAL,
            PrqlTypes.TIME_LITERAL,
            PrqlTypes.TIMESTAMP_LITERAL,
            PrqlTypes.INTERVAL_LITERAL -> SqlColors.SQL_VARIABLE

            PrqlTypes.NULL,
            PrqlTypes.BOOL_FALSE,
            PrqlTypes.BOOL_TRUE -> SqlColors.SQL_SYNTHETIC_ENTITY

            PrqlTypes.AGGREGATE_FUNCTION -> SqlColors.SQL_PROCEDURE
            else -> {
                null
            }
        }
        return textAttributesKey?.let { arrayOf(it) } ?: emptyArray()
    }
}