package com.test.wadiz.data

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("body") val body: Body
)
