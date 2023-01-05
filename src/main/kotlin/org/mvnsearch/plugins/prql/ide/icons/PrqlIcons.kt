package org.mvnsearch.plugins.prql.ide.icons

import com.intellij.icons.AllIcons
import com.intellij.openapi.util.IconLoader

object PrqlIcons {
    val PRQL_FILE = IconLoader.findIcon("/icons/prql-16x16.png", PrqlIcons::class.java.classLoader)!!
    val RUN_ICON = AllIcons.RunConfigurations.TestState.Run
}