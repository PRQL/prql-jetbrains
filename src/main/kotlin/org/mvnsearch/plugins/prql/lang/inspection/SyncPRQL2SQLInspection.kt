package org.mvnsearch.plugins.prql.lang.inspection

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.util.parentOfType
import org.mvnsearch.plugins.prql.Prql


class SyncPRQL2SQLInspection : AbstractBaseJavaLocalInspectionTool() {
    private val syncPRQL2SQLQuickFix = SyncPRQL2SQLQuickFix()

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : JavaElementVisitor() {
            override fun visitAnnotation(prqlAnnotation: PsiAnnotation) {
                super.visitAnnotation(prqlAnnotation)
                val qualifiedName = prqlAnnotation.qualifiedName
                if (qualifiedName != null) {
                    if (qualifiedName.endsWith("PRQL")) {
                        prqlAnnotation.parentOfType<PsiMethod>(false)?.let { psiMethod ->
                            if (psiMethod.hasAnnotation("org.springframework.data.jpa.repository.Query")) {
                                psiMethod.getAnnotation("org.springframework.data.jpa.repository.Query")?.let { queryAnnotation ->
                                    var sql = ""
                                    var prql = ""
                                    queryAnnotation.findAttributeValue("value")?.let { sqlValue ->
                                        sql = sqlValue.text.trim('"')
                                    }
                                    prqlAnnotation.findAttributeValue("value")?.let { prqlValue ->
                                        prql = prqlValue.text.trim('"')
                                    }
                                    if (prql.isNotEmpty()) {
                                        val transpiledSQL = Prql.transformPrql(null, prql, false, prqlAnnotation.project)
                                        if (transpiledSQL != sql) {
                                            holder.registerProblem(prqlAnnotation, "Sync PRQL to SQL", syncPRQL2SQLQuickFix)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


class SyncPRQL2SQLQuickFix : LocalQuickFix {
    override fun getFamilyName(): String {
        return "Sync PRQL to SQL"
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val prqlAnnotation = descriptor.startElement as PsiAnnotation
        prqlAnnotation.parentOfType<PsiMethod>(false)?.let { psiMethod ->
            psiMethod.getAnnotation("org.springframework.data.jpa.repository.Query")?.let { queryAnnotation ->
                var prql = ""
                prqlAnnotation.findAttributeValue("value")?.let { prqlValue ->
                    prql = prqlValue.text.trim('"')
                }
                if (prql.isNotEmpty()) {
                    val transpiledSQL = Prql.transformPrql(null, prql, false, project)
                    val newAttributeExpression = JavaPsiFacade.getElementFactory(project).createExpressionFromText("\"$transpiledSQL\"", queryAnnotation)
                    queryAnnotation.setDeclaredAttributeValue("value", newAttributeExpression)
                    queryAnnotation.setDeclaredAttributeValue("nativeQuery", JavaPsiFacade.getElementFactory(project).createExpressionFromText("true", queryAnnotation))
                }
            }
        }
    }
}
