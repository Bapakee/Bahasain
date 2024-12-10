package com.bahasain.ui.learn.quiz

import com.bahasain.ui.placement.Placement

sealed class QuizModel {
    data class optionsQuiz(
        val id: Int,
        val question: String,
        val imageUrl: String,
        val quizOptions: List<String>,
        val answer: Int,
        var userAnswer: Int? = null
    ) : QuizModel()
}