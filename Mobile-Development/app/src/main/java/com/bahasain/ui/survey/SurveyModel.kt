package com.bahasain.ui.survey

data class SurveyModel(
    val id: Int,
    val survey: String,
    val descSurvey: String,
    val optionsSurvey: List<String>,
    val correctAnswer: List<Int>
)