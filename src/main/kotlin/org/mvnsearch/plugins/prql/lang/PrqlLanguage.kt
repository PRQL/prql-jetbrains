package org.mvnsearch.plugins.prql.lang

import com.intellij.lang.Language

object PrqlLanguage : Language("PRQL") {
    override fun isCaseSensitive() = true
}