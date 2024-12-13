package com.bahasain.utils

fun setLevel(level: Int): String = when (level) {
    1 -> "Basic"
    2 -> "Intermediate"
    3 -> "Advance"
    else -> "Unknown Level"
}
