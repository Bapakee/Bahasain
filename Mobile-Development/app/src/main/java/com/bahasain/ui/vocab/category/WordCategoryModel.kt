package com.bahasain.ui.vocab.category

sealed class WordCategoryModel {
    data class Header(
        val title: String
    ) : WordCategoryModel()

    data class WordItem(
        val id: String,
        val word: String
    ) : WordCategoryModel()
}