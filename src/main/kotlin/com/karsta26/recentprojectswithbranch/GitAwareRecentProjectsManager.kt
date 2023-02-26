package com.karsta26.recentprojectswithbranch

import com.intellij.ide.RecentProjectsManagerBase
import com.intellij.openapi.project.Project
import com.intellij.platform.ModuleAttachProcessor
import git4idea.GitUtil
import git4idea.repo.GitBranchTrackInfo

class GitAwareRecentProjectsManager : RecentProjectsManagerBase() {
    override fun getProjectDisplayName(project: Project): String {
        val baseName = ModuleAttachProcessor.getMultiProjectDisplayName(project) ?: project.name
        val currentBranch = getCurrentBranch(project)
        if (currentBranch != null) {
            return "$baseName [@${currentBranch.localBranch.name}]"
        }
        return baseName
    }

    private fun getCurrentBranch(project: Project): GitBranchTrackInfo? {
        val repositoryManager = GitUtil.getRepositoryManager(project)
        val gitRepository = repositoryManager.repositories.firstOrNull()
        if (gitRepository != null) {
            return GitUtil.getTrackInfoForCurrentBranch(gitRepository)
        }
        return null
    }
}