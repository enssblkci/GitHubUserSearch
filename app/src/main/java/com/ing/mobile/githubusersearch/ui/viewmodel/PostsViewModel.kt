package com.ing.mobile.githubusersearch.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ing.mobile.githubusersearch.base.BaseViewModel
import com.ing.mobile.githubusersearch.data.search.usecase.GetPostsUseCase
import com.ing.mobile.githubusersearch.model.search.UseCaseResponse
import com.ing.mobile.githubusersearch.model.search.ErrorModel
import com.ing.mobile.githubusersearch.model.search.GitHubUser
import com.ing.mobile.githubusersearch.model.search.LocalUserData
import kotlinx.coroutines.ExperimentalCoroutinesApi

class PostsViewModel constructor(private val getPostsUseCase: GetPostsUseCase) : BaseViewModel() {

    val showProgressbar = MutableLiveData<Boolean>()

    val messageData = MutableLiveData<String>()
    private val _postsData = MutableLiveData<List<GitHubUser>>()
    val postsData: LiveData<List<GitHubUser>> = _postsData

    private val _searchClickListener= MutableLiveData<Unit>()
    val searchClickListener: LiveData<Unit> = _searchClickListener

    var keyword = MutableLiveData<String>()

    @ExperimentalCoroutinesApi
    fun getPosts(userName: String) {
        showProgressbar.value = true
        getPostsUseCase.invoke(userName, object : UseCaseResponse<List<GitHubUser>> {
            override fun onSuccess(result: List<GitHubUser>) {
                _postsData.value = result
                showProgressbar.value = false
            }
            override fun onError(errorModel: ErrorModel?) {
                messageData.value = errorModel?.message
                showProgressbar.value = false
            }
        })
    }
    fun onChanged(text: CharSequence) {
        if (text.isNotEmpty()) {
            keyword.value = text.toString()
        }
    }

    fun searchOnClickListener() {
        _searchClickListener.value = Unit
    }
    fun createLocalUserData(gitHubUser: GitHubUser): LocalUserData {
        return LocalUserData(
            repoID = gitHubUser.id!!,
            repoName = gitHubUser.repoName,
            avatarUrl = gitHubUser.owner.avatarUrl ?: "",
            starCount = gitHubUser.starCount,
            openIssuesCount = gitHubUser.openIssuesCount,
            userName = gitHubUser.owner.userName ?: "",
            isFavorite = gitHubUser.isFavorite.not()
        )
    }

}