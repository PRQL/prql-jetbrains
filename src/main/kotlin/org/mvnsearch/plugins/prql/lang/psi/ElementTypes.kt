package org.mvnsearch.plugins.prql.lang.psi

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.psi.tree.IElementType
import org.mvnsearch.plugins.prql.ide.icons.PrqlIcons
import org.mvnsearch.plugins.prql.lang.PrqlLanguage
import javax.swing.Icon

class PrqlTokenType(debugName: String) : IElementType(debugName, PrqlLanguage) {
    override fun toString(): String = "PrqlToken." + super.toString()
}

class PrqlElementType(debugName: String) : IElementType(debugName, PrqlLanguage)

object PrqlFileType : LanguageFileType(PrqlLanguage) {
    override fun getName(): String = "prql"
    override fun getDescription(): String = "PRQL file"
    override fun getDefaultExtension(): String = "prql"

    override fun getIcon(): Icon = PrqlIcons.PRQL_FILE
}