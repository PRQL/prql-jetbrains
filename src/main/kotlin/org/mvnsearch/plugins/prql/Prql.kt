package org.mvnsearch.plugins.prql

import com.intellij.openapi.util.SystemInfo
import java.io.File

object Prql {
    fun getPrqlCompilerCmdAbsolutionPath(): String {
        val userHome = System.getProperty("user.home")
        if (File(userHome, ".cargo/bin/prql-compiler").exists()) {
            return File(userHome, ".cargo/bin/prql-compiler").absolutePath
        }
        return if (SystemInfo.isWindows) {
            return "prql-compiler"
        } else {
            if (File("/usr/local/bin/prql-compiler").exists()) {
                "/usr/local/bin/prql-compiler"
            } else {
                "prql-compiler"
            }
        }
    }
}