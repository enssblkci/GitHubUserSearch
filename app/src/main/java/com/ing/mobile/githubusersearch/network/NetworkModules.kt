package com.ing.mobile.githubusersearch.network

import com.ing.mobile.githubusersearch.network.exception.ApiErrorHandle
import com.ing.mobile.githubusersearch.BuildConfig
import com.ing.mobile.githubusersearch.data.search.usecase.GetPostsUseCase
import com.ing.mobile.githubusersearch.data.search.api.RepoApi
import com.ing.mobile.githubusersearch.repository.PostsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val NetworkModules = module {

    single { createService(get()) }

    single { createRetrofit(get(), BuildConfig.BASE_URL) }

    single { createOkHttpClient() }

}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun createApiErrorHandle(): ApiErrorHandle {
    return ApiErrorHandle()
}

fun createService(retrofit: Retrofit): RepoApi {
    return retrofit.create(RepoApi::class.java)
}

fun createPostRepository(apiService: RepoApi): PostsRepository {
    return PostsRepository(apiService)
}

fun createGetPostsUseCase(
    postsRepository: PostsRepository,
    apiErrorHandle: ApiErrorHandle
): GetPostsUseCase {
    return GetPostsUseCase(postsRepository, apiErrorHandle)
}
