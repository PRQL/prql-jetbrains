package org.mvnsearch.plugins.prql.lang.run

import com.intellij.execution.lineMarker.RunLineMarkerProvider
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import com.intellij.sql.dialects.SqlDialectMappings
import org.mvnsearch.plugins.prql.Prql
import org.mvnsearch.plugins.prql.lang.psi.PrqlTypes
import org.prql.prql4j.PrqlCompiler


open class PrqlBaseLienMarkerContributor : RunLineMarkerProvider() {

    @Throws(Exception::class)
    fun transformPrql(dialect: String?, prqlCode: String, project: Project): String {
        val prqlDialect = if (dialect != null) {
            Prql.getTarget(dialect)
        } else {
            val defaultDialect = SqlDialectMappings.getMapping(project, null)
            Prql.getTarget(defaultDialect.id)
        } ?: "generic"
        return PrqlCompiler.toSql(prqlCode, prqlDialect, true, false)
    }

    fun isPrqlFromElement(psiElement: PsiElement): Boolean {
        val elementType = psiElement.elementType
        return elementType == PrqlTypes.STMT_FROM || elementType == PrqlTypes.STMT_FROM_TEXT
    }

    fun raiseError(project: Project, title: String, message: String) {
        val prqlNotificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("PRQL Error")
        prqlNotificationGroup.createNotification(title, message, NotificationType.ERROR).notify(project)
    }
}