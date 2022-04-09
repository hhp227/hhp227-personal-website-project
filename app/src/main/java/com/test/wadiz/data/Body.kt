package com.test.wadiz.data

import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("list") val list: List<ListData>
)
