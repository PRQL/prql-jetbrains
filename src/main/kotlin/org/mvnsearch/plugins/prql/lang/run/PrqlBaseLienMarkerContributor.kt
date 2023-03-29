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


    fun isPrqlFromElement(psiElement: PsiElement): Boolean {
        val elementType = psiElement.elementType
        return elementType == PrqlTypes.STMT_FROM || elementType == PrqlTypes.STMT_FROM_TEXT
    }

    fun raiseError(project: Project, title: String, message: String) {
        val prqlNotificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("PRQL Error")
        prqlNotificationGroup.createNotification(title, message, NotificationType.ERROR).notify(project)
    }
}