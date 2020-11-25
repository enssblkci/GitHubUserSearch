package com.ing.mobile.githubusersearch.data.search.api

import com.ing.mobile.githubusersearch.model.search.GitHubUser
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoApi{
    /** retrofit
     * Get github users
     */
    @GET("users/{user}/repos")
    suspend fun userRepos(@Path("user") username: String): List<GitHubUser>

}