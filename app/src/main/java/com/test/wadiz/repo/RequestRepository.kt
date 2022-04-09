package com.test.wadiz.repo

import com.test.wadiz.request.ApiRequestFactory

class RequestRepository {

    suspend fun requestSearch(keyword: String) = ApiRequestFactory.retrofit.requestSearch(keyword)

}