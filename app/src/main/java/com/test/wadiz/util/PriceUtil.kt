package com.test.wadiz.util

import java.text.DecimalFormat

object PriceUtil {
    fun getPrice(price: Int): String {
        val formatter = DecimalFormat("###,###")
        return "${formatter.format(price)}원"
    }
}