package com.example.hellokittyquiz

import androidx.annotation.StringRes
import java.util.*

data class MCQuestion(val id: UUID = UUID.randomUUID(),
                      val questText: String = "",
                      val questAnswers: Array<String> = emptyArray(),
                      val correctAnswer: Int = 0,
                      var answered: Boolean = false)