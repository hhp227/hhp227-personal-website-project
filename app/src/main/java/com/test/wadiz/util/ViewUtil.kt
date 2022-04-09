package com.test.wadiz.util

import android.view.View

fun Boolean.toVisibility(): Int {
    return if (this) View.VISIBLE else View.GONE
}