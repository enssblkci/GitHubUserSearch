package com.ing.mobile.githubusersearch.repository

import com.ing.mobile.githubusersearch.data.search.api.RepoApi
import com.ing.mobile.githubusersearch.model.search.GitHubUser

class PostsRepository(private val apiService: RepoApi) : BasePostRepository {
    override suspend fun getUserPosts(user:String): List<GitHubUser> {
        return apiService.userRepos(user)
    }
}