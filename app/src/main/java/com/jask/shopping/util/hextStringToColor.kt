package com.jask.shopping.util

import androidx.compose.ui.graphics.Color

fun hexStringToColor(hexString: String): Color {
    val colorInt = hexString.toLong(16).toInt()
    return Color(colorInt)
}