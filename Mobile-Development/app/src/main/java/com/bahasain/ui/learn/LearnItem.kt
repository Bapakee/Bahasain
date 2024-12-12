package com.bahasain.ui.learn

import com.bahasain.data.remote.response.learn.DataItemLearn

sealed class LearnItem {
    data class Heading(val level: Int) : LearnItem()
    data class Module(val data: DataItemLearn?) : LearnItem()
}