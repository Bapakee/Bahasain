package com.bahasain.utils

import android.content.Context

fun loadPlacementQuizFromJson(context: Context, resId: Int): String {
    return context.resources.openRawResource(resId).bufferedReader().use { it.readText() }
}



