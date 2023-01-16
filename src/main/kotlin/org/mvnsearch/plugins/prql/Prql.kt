package org.mvnsearch.plugins.prql

import com.intellij.openapi.util.SystemInfo
import java.io.File

object Prql {
    fun getPrqlCompilerCmdAbsolutionPath(): String {
        val userHome = System.getProperty("user.home")
        if (File(userHome, ".cargo/bin/prqlc").exists()) {
            return File(userHome, ".cargo/bin/prqlc").absolutePath
        }
        return if (SystemInfo.isWindows) {
            return "prqlc"
        } else {
            if (File("/usr/local/bin/prqlc").exists()) {
                "/usr/local/bin/prqlc"
            } else {
                "prqlc"
            }
        }
    }
}