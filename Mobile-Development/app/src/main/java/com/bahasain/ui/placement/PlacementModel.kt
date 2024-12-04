package com.bahasain.ui.placement

sealed class Placement {
    data class SingleChoice(
        val quizTitle: String,
        val quiz: String,
        val optionsQuiz: List<String>,
        val correctAnswer: Int,
        var userAnswer: Int? = null
    ) : Placement()

    data class MultipleChoice(
        val quizTitle: String,
        val quiz: String,
        val optionsQuiz: List<String>,
        val correctAnswers: List<Int>,
        var userAnswers: List<Int> = emptyList()
    ) : Placement()

    data class Matching(
        val quizTitle: String,
        val quiz: String,
        val pairs: List<Pair<String, String>>,
        var userMatches: Map<String, String> = emptyMap()
    ) : Placement()
}

