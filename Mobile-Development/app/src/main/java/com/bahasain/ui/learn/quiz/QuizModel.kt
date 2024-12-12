package com.bahasain.ui.learn.quiz

data class QuizModel(
    val id: Int,
    val type: String,
    val question: String,
    val imageUrl: String,
    val quizOptions: List<String>,
    val correctOption: Int? = null,
    val correctOrder: List<String>? = null,
    var userAnswer: Any? = null
)

