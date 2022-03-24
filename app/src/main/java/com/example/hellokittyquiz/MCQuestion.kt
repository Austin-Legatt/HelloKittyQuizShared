package com.example.hellokittyquiz

import androidx.annotation.StringRes
import java.util.*

data class MCQuestion(val id: UUID = UUID.randomUUID(), val questText: String, val questAnswers: Array<String>, val correctAnswer: Int, var answered: Boolean = false)