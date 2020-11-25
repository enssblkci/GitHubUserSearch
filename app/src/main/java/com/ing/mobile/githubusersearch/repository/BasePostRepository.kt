package com.ing.mobile.githubusersearch.repository

import com.ing.mobile.githubusersearch.model.search.GitHubUser

interface BasePostRepository {
    suspend fun getUserPosts(user: String): List<GitHubUser>
}