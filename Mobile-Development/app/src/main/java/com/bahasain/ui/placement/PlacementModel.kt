package com.bahasain.ui.placement

sealed class Placement{
    data class SingleChoice(
        val quizTitle: String,
        val quiz: String,
        val optionsQuiz: List<String>,
        val correctAnswer: Int
    ): Placement()

    data class MultipleChoice(
        val quizTitle: String,
        val quiz: String,
        val optionsQuiz: List<String>,
        val correctAnswer: List<Int>
    ): Placement()

    data class Matching(
        val quizTitle: String,
        val quiz: String,
        val pairs: List<Pair<String, String>>,
    ): Placement()
}
