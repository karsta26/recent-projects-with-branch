package com.karsta26.recentprojectswithbranch

import com.intellij.ide.RecentProjectsManagerBase
import com.intellij.openapi.project.Project
import com.intellij.platform.ModuleAttachProcessor
import git4idea.GitUtil
import kotlinx.coroutines.CoroutineScope

class GitAwareRecentProjectsManager(coroutineScope: CoroutineScope) : RecentProjectsManagerBase(coroutineScope) {
    override fun getProjectDisplayName(project: Project): String {
        val baseName = ModuleAttachProcessor.getMultiProjectDisplayName(project) ?: project.name
        val currentBranch = getCurrentBranch(project)
        if (currentBranch != null) {
            return "$baseName [@${currentBranch}]"
        }
        return baseName
    }

    private fun getCurrentBranch(project: Project): String? {
        val repositoryManager = GitUtil.getRepositoryManager(project)
        val gitRepository = repositoryManager.repositories.firstOrNull()
        return gitRepository?.currentBranch?.name
    }
}