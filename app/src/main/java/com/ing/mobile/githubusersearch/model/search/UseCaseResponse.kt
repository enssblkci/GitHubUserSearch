package com.ing.mobile.githubusersearch.model.search

interface UseCaseResponse<Type> {
    fun onSuccess(result: Type)
    fun onError(errorModel: ErrorModel?)
}

