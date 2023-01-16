package org.mvnsearch.plugins.prql.lang.run

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.database.dataSource.DatabaseConnectionManager
import com.intellij.database.dataSource.connection.statements.SmartStatements
import com.intellij.execution.lineMarker.RunLineMarkerProvider
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import org.jetbrains.concurrency.runAsync
import org.mvnsearch.plugins.prql.Prql
import org.mvnsearch.plugins.prql.ide.icons.PrqlIcons
import org.mvnsearch.plugins.prql.lang.psi.PrqlTypes
import java.awt.datatransfer.StringSelection
import javax.swing.Icon

@Suppress("DialogTitleCapitalization")
class PrqlRunLineMarkerContributor : RunLineMarkerProvider() {
    override fun getName(): String {
        return "prql-transpile-sql"
    }

    override fun getIcon(): Icon {
        return icons.DatabaseIcons.Sql
    }

    override fun getLineMarkerInfo(psiElement: PsiElement): LineMarkerInfo<*>? {
        val elementType = psiElement.elementType
        if (elementType == PrqlTypes.STMT_FROM) {
            val text = psiElement.containingFile.text
            return LineMarkerInfo(
                psiElement,
                psiElement.textRange,
                icon,
                {
                    "Copy generated SQL to clipboard"
                },
                { e, elt ->
                    try {
                        var sqlOrError = transformPrql(text + "\n").trim()
                        if (sqlOrError.contains("-- Generated by PRQL")) {
                            sqlOrError = sqlOrError.substring(0, sqlOrError.indexOf("-- Generated by PRQL")).trim()
                        }
                        if (sqlOrError.startsWith("Error:")) {
                            val prqlNotificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("PRQL Error")
                            prqlNotificationGroup.createNotification(
                                "Error generating SQL!", sqlOrError, NotificationType.ERROR
                            ).notify(psiElement.project)
                        } else {   // compiled successfully
                            CopyPasteManager.getInstance().setContents(StringSelection(sqlOrError))
                            val prqlNotificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("PRQL Info")
                            prqlNotificationGroup.createNotification(
                                "SQL generated and copied to clipboard!",
                                sqlOrError, NotificationType.INFORMATION
                            ).notify(psiElement.project)
                        }
                    } catch (e: Exception) {
                        val prqlNotificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("PRQL Error")
                        prqlNotificationGroup.createNotification(
                            "Error generating SQL!", e.message!!, NotificationType.ERROR
                        ).notify(psiElement.project)
                    }
                },
                GutterIconRenderer.Alignment.CENTER,
                { "Copy generated SQL to clipboard" }
            )
        }
        return null
    }

    private fun transformPrql(prqlCode: String): String {
        val process = ProcessBuilder().command(Prql.getPrqlCompilerCmdAbsolutionPath(), "compile").start();
        process.outputStream.use {
            it.write(prqlCode.toByteArray())
        }
        process.waitFor()
        return if (process.exitValue() == 0) {
            process.inputStream.bufferedReader().readText()
        } else {
            process.errorStream.bufferedReader().readText()
        }
    }

    fun runSQL(project: Project) {
        val databaseConnectionManager = DatabaseConnectionManager.getInstance()
        databaseConnectionManager.activeConnections.firstOrNull()?.let { connection ->
            runAsync {
                SmartStatements.poweredBy(connection).simple().execute("select * from people")
            }
        }
    }
}
