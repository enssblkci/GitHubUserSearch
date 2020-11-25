package com.ing.mobile.githubusersearch.di

import com.ing.mobile.githubusersearch.network.*
import com.ing.mobile.githubusersearch.ui.viewmodel.PostsViewModel
import com.ing.mobile.githubusersearch.ui.viewmodel.UserDetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.math.sin

val userSearchDI = module {
    viewModel { PostsViewModel(get()) }
    viewModel { UserDetailViewModel() }
    single { createGetPostsUseCase(get(), createApiErrorHandle()) }
    single { createPostRepository(get()) }
}

