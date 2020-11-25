package com.ing.mobile.githubusersearch.ui.adapter

import com.ing.mobile.githubusersearch.model.search.GitHubUser

interface UserSearchHandler {
    fun selectedItem(gitHubUser: GitHubUser)
}

