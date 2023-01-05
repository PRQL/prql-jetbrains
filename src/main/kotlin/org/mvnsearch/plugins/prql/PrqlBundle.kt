package org.mvnsearch.plugins.prql

import com.intellij.DynamicBundle
import org.jetbrains.annotations.PropertyKey

import org.jetbrains.annotations.NonNls

@NonNls
private const val BUNDLE = "messages.PrqlBundle"

object PrqlBundle : DynamicBundle(BUNDLE) {

    @Suppress("SpreadOperator")
    @JvmStatic
    fun message(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) =
        getMessage(key, *params)

    @Suppress("SpreadOperator", "unused")
    @JvmStatic
    fun messagePointer(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) =
        getLazyMessage(key, *params)
}