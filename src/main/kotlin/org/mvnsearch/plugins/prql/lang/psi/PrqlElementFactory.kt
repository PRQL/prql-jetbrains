package org.mvnsearch.plugins.prql.lang.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory


object PrqlElementFactory {

    fun createFile(project: Project, text: String) =
        PsiFileFactory.getInstance(project).createFileFromText("demo.prql", PrqlFileType, text) as PrqlFile


    fun createTableDefSQLCodeBlock(project: Project, codeBlock: String): PrqlStmtTableFromSql {
        val recipeStatement = createFile(project, "table demo = $codeBlock").firstChild as PrqlStmtTableDef
        return recipeStatement.stmtTableFromSql!!
    }

    fun createTableDefWithSQL(project: Project, tableName: String): PrqlStmtTableDef {
        return createFile(project, "table $tableName = \$\"\"\"\"\"\"").firstChild as PrqlStmtTableDef
    }

    fun createTableFromSQL(project: Project, codeBlock: String): PrqlTableFromSql {
        val stmtFrom = createFile(project, "from $codeBlock").firstChild as PrqlStmtFrom
        return stmtFrom.tableVariant!!.firstChild as PrqlTableFromSql
    }

    fun createTableFromText(project: Project, format:String, codeBlock: String): PrqlStmtFromTextStringArg {
          val stmtFrom = createFile(project, "from_text format:$format $codeBlock").firstChild as PrqlStmtFrom
          return stmtFrom.tableVariant!!.lastChild as PrqlStmtFromTextStringArg
      }

}