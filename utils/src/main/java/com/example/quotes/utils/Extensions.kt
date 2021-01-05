package com.example.quotes.utils

import android.content.Context
import android.util.DisplayMetrics
import kotlin.math.roundToInt

fun Context.convertDpToPixel(dp: Float): Int =
    (dp * (this.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()