package org.mvnsearch.plugins.prql.lang.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.icons.AllIcons
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import org.mvnsearch.plugins.prql.lang.psi.PrqlElementFactory.createTableDefWithSQL
import javax.swing.Icon

interface PrqlNamedElement : PsiNameIdentifierOwner {
    fun getKey(): String?

    fun getValue(): String?

    override fun getName(): String?

    override fun setName(name: String): PsiElement?

    override fun getNameIdentifier(): PsiElement?

    fun getPresentation(): ItemPresentation?
}

abstract class PrqlNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), PrqlNamedElement {
    private var _name: String? = null

    override fun getName(): String? {
        return this._name
    }

    override fun setName(name: String): PsiElement? {
        this._name = name
        return this
    }

    override fun getNameIdentifier(): PsiElement? {
        return this
    }

    override fun getPresentation(): ItemPresentation? {
        return null
    }

}

abstract class PrqlStmtTableDefElementImpl(node: ASTNode) : PrqlNamedElementImpl(node) {
    override fun getKey(): String? {
        val keyNode: ASTNode? = this.node.findChildByType(PrqlTypes.TABLE_NAME)
        return keyNode?.text
    }

    override fun getValue(): String? {
        val valueNode: ASTNode? = this.node.findChildByType(PrqlTypes.STMT_TABLE_FROM_SQL)
        return valueNode?.text
    }

    override fun getName(): String? {
        return getKey()
    }

    override fun setName(name: String): PsiElement? {
        val keyNode: ASTNode? = this.node.findChildByType(PrqlTypes.STMT_TABLE_DEF)
        if (keyNode != null) {
            val tableDef = createTableDefWithSQL(this.project, name)
            val newKeyNode = tableDef.tableName!!.node
            this.node.replaceChild(keyNode, newKeyNode)
        }
        return this
    }

    override fun getNameIdentifier(): PsiElement? {
        return this.node.findChildByType(PrqlTypes.STMT_TABLE_DEF)?.psi
    }

    override fun getPresentation(): ItemPresentation? {
        val presentationText = this.getKey()
        val element = this
        return object : ItemPresentation {
            override fun getPresentableText(): String? {
                return presentationText
            }

            override fun getLocationString(): String? {
                return element.containingFile?.name
            }

            override fun getIcon(unused: Boolean): Icon {
                return AllIcons.RunConfigurations.TestState.Run
            }
        }
    }
}
