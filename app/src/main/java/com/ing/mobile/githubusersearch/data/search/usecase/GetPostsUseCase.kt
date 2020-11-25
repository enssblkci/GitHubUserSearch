package com.ing.mobile.githubusersearch.data.search.usecase

import com.ing.mobile.githubusersearch.base.BaseUseCase
import com.ing.mobile.githubusersearch.network.exception.ApiErrorHandle

import com.ing.mobile.githubusersearch.model.search.GitHubUser
import com.ing.mobile.githubusersearch.repository.PostsRepository

class GetPostsUseCase constructor(
    private val postsRepository: PostsRepository,
    apiErrorHandle: ApiErrorHandle
) : BaseUseCase<List<GitHubUser>, Any?>(apiErrorHandle) {

    override suspend fun run(params: Any?): List<GitHubUser> {
        return postsRepository.getUserPosts(params.toString())
    }
}

