package org.mvnsearch.plugins.prql.lang.lighter

import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.mvnsearch.plugins.prql.lang.lighter.PrqlSyntaxHighlighter


class PrqlSyntaxHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
        return PrqlSyntaxHighlighter()
    }
}