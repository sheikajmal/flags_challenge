package com.flagschallenge.app.domain.model


data class Questions(
    val questions: List<Question>
)

data class Question(
    val answer_id: Int,
    val countries: List<Country>,
    val country_code: String
)

data class Country(
    val country_name: String,
    val id: Int
)
