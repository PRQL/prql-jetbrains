package org.mvnsearch.plugins.prql.lang.run

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.database.console.JdbcConsole
import com.intellij.database.console.JdbcConsoleService
import com.intellij.database.datagrid.DataRequest
import com.intellij.database.util.DbImplUtilCore
import com.intellij.icons.AllIcons
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.intellij.util.ObjectUtils
import javax.swing.Icon

@Suppress("DialogTitleCapitalization")
class PrqlRunLineMarkerContributor : PrqlBaseLienMarkerContributor() {
    override fun getName(): String {
        return "prql-run-sql"
    }

    override fun getIcon(): Icon {
        return AllIcons.Actions.Execute
    }

    override fun getLineMarkerInfo(psiElement: PsiElement): LineMarkerInfo<*>? {
        if (isPrqlFromElement(psiElement)) {
            val text = psiElement.containingFile.text
            return LineMarkerInfo(
                psiElement,
                psiElement.textRange,
                icon,
                {
                    "Run generated SQL on JDBC Console"
                },
                { e, elt ->
                    try {
                        val consoles = JdbcConsoleService.getInstance().getActiveConsoles(psiElement.project)
                        if (consoles.isEmpty()) {
                            raiseError(psiElement.project, "Failed to find active JDBC Console", "Please setup database and open a JDBC Console first")
                        } else {
                            val jdbcConsole = ObjectUtils.tryCast(consoles[0], JdbcConsole::class.java)!!
                            val sqlOrError = transformPrql(jdbcConsole.dataSource.defaultDialect, text + "\n", elt.project).trim()
                            if (sqlOrError.startsWith("ERROR:") || sqlOrError.startsWith("Error:")) {
                                raiseError(psiElement.project, "Failed to generate SQL!", sqlOrError)
                            } else {   // compiled successfully
                                val sqlQuery = sqlOrError
                                ApplicationManager.getApplication().runWriteAction {
                                    jdbcConsole.consoleView.editorDocument.setText(sqlQuery)
                                }
                                val queryRequest =
                                    DataRequest.newRequest(jdbcConsole, sqlQuery, 0, DbImplUtilCore.getPageSize(jdbcConsole.dataSource.dbms), 0, 0)
                                jdbcConsole.messageBus.dataProducer.processRequest(queryRequest)
                            }
                        }
                    } catch (e: Exception) {
                        raiseError(psiElement.project, "Failed to generate SQL!", e.message!!)
                    }
                },
                GutterIconRenderer.Alignment.CENTER,
                { "Run generated SQL on JDBC Console" }
            )
        }
        return null
    }

}
