package com.bahasain.ui.learn.quiz

data class QuizModel(
    val id: Int,
    val question: String,
    val imageUrl: String,
    val quizOptions: List<String>,
    val answer: Int,
    var userAnswer: Int? = null
)
