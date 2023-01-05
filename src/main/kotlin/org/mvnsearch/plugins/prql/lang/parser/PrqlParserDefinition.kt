package org.mvnsearch.plugins.prql.lang.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.mvnsearch.plugins.prql.lang.PrqlLanguage
import org.mvnsearch.plugins.prql.lang.lexer.PrqlLexerAdapter
import org.mvnsearch.plugins.prql.lang.psi.PrqlFile
import org.mvnsearch.plugins.prql.lang.psi.PrqlTypes


class PrqlParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?): Lexer = PrqlLexerAdapter()

    override fun getWhitespaceTokens(): TokenSet = WHITE_SPACES

    override fun getCommentTokens(): TokenSet = COMMENTS

    override fun getStringLiteralElements(): TokenSet = LITERALS

    override fun createParser(project: Project?): PsiParser = PrqlParser()

    override fun getFileNodeType(): IFileElementType = FILE

    override fun createFile(viewProvider: FileViewProvider): PsiFile = PrqlFile(viewProvider)

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements =
        ParserDefinition.SpaceRequirements.MAY

    override fun createElement(node: ASTNode?): PsiElement = PrqlTypes.Factory.createElement(node)

    companion object {
        val WHITE_SPACES: TokenSet = TokenSet.create(TokenType.WHITE_SPACE)
        val COMMENTS: TokenSet = TokenSet.create(PrqlTypes.COMMENT)
        val LITERALS: TokenSet = TokenSet.create(PrqlTypes.STRING_LITERAL,  PrqlTypes.RAW_LITERAL, PrqlTypes.CHAR_LITERAL, PrqlTypes.INDENTED_STRING)
        val FILE: IFileElementType = IFileElementType(PrqlLanguage)
    }
}