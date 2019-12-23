package com.guet.flexbox.handshake

import com.intellij.CommonBundle
import com.intellij.icons.AllIcons
import com.intellij.ide.actions.CreateElementActionBase
import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.codeStyle.CodeStyleManager

class NewFlexmlAction : CreateElementActionBase("", "", AllIcons.FileTypes.Xml) {

    override fun invokeDialog(project: Project?, directory: PsiDirectory?): Array<PsiElement> {
        val inputValidator = this.MyInputValidator(project, directory)
        Messages.showInputDialog(
            project,
            "创建一个新的flexml文件",
            "创建一个新的flexml文件",
            null,
            "",
            inputValidator
        )
        return inputValidator.createdElements
    }

    override fun create(newName: String, directory: PsiDirectory): Array<PsiElement> {
        val ext = ".${FlexmlFileType.defaultExtension}"
        val filename = if (newName.endsWith(ext)) {
            newName
        } else {
            newName + ext
        }
        val template = FileTemplateManager.getInstance(directory.project)
            .getInternalTemplate("flexml_file")
        val text = template.text
        val factory = PsiFileFactory.getInstance(directory.project)
        val file = factory.createFileFromText(filename, FlexmlFileType, text)
        CodeStyleManager.getInstance(directory.project).reformat(file)
        directory.add(file)
        return arrayOf(file)
    }

    override fun getActionName(directory: PsiDirectory?, newName: String?): String = "Flexml File"

    override fun getCommandName(): String = "Create Flexml File"

    override fun getErrorTitle(): String = CommonBundle.getErrorTitle()

}