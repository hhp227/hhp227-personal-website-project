package com.test.wadiz.request

import com.test.wadiz.data.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("search")
    suspend fun requestSearch(@Query("keyword") keyword: String): Response
}