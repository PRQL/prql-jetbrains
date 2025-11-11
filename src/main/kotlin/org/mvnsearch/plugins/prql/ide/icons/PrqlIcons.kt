package org.mvnsearch.plugins.prql.ide.icons

import com.intellij.openapi.util.IconLoader

object PrqlIcons {
    val PRQL_FILE = IconLoader.findIcon("/icons/prql-16x16.png", PrqlIcons::class.java.classLoader)!!
    val COPY_ICON = com.intellij.icons.AllIcons.Actions.Copy
    val TO_SQL_ICON = IconLoader.findIcon("/icons/tosql.svg", PrqlIcons::class.java.classLoader)!!
}