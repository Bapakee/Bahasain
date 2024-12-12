package com.bahasain.ui.placement

sealed class Placement {
    data class Matching(
        val id: Int,
        val quizTitle: String,
        val quiz: String,
        val pairs: List<Pair>,
        var userMatches: MutableMap<String, String> = mutableMapOf()
    ) : Placement()

    data class SingleChoice(
        val id: Int,
        val quizTitle: String,
        val textReading : String,
        val quiz: String,
        val optionsQuiz: List<String>,
        val correctAnswer: Int,
        var userAnswer: Int? = null
    ) : Placement()

    data class MultipleChoice(
        val id: Int,
        val quizTitle: String,
        val textReading : String,
        val quiz: String,
        val optionsQuiz: List<String>,
        val correctAnswers: List<Int>,
        var userAnswers: List<Int> = emptyList()
    ) : Placement()
}

data class Pair(
    val indonesia: String,
    val english: String
)



