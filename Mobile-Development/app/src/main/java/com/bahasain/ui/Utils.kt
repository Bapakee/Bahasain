package com.bahasain.ui

fun setLevel(level: Int): String = when (level) {
    1 -> "Basic"
    2 -> "Intermediate"
    3 -> "Expert"
    else -> "Unknown Level"
}
