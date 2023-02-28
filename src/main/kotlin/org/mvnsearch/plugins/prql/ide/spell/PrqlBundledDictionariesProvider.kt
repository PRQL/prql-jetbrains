package org.mvnsearch.plugins.prql.ide.spell

import com.intellij.spellchecker.BundledDictionaryProvider

class PrqlBundledDictionariesProvider : BundledDictionaryProvider {
    override fun getBundledDictionaries(): Array<String> {
        return arrayOf("prql.dic")
    }
}