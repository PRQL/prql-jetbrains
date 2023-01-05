package org.mvnsearch.plugins.prql.lang.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider
import org.mvnsearch.plugins.prql.lang.PrqlLanguage

class PrqlFile(viewProvider: FileViewProvider?) : PsiFileBase(viewProvider!!, PrqlLanguage) {
    override fun getFileType() = PrqlFileType
    override fun toString() = "PRQL File"

    fun findAllFunctionNames(): List<String> {
        return this.children
            .filterIsInstance<PrqlStmtFuncDef>()
            .map {
                it.text
            }.toList()
    }
}

